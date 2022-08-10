package com.sh.membership.common.exception

import com.sh.membership.common.response.CommonResponse
import com.sh.membership.common.response.ErrorCode
import org.apache.catalina.connector.ClientAbortException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.NestedExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class CommonExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(CommonExceptionHandler::class.java)

    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun onException(e: Exception): CommonResponse<Any> {
        log.error("exception", e)

        return CommonResponse.fail(e.message, ErrorCode.COMMON_SYSTEM_ERROR.name)
    }

    /**
     * http status: 200 AND result: FAIL
     * 시스템은 이슈 없고, 비즈니스 로직 처리에서 에러가 발생함
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = [BaseException::class])
    fun onBaseException(e: BaseException): CommonResponse<Any> {
        log.warn(
            "[BaseException] cause = {}, errorMsg = {}",
            NestedExceptionUtils.getMostSpecificCause(e),
            NestedExceptionUtils.getMostSpecificCause(e).message
        )

        return CommonResponse.fail(e.message, e.errorCode.name)
    }

    /**
     * 예상치 않은 Exception 중에서 모니터링 skip 이 가능한 Exception 을 처리할 때
     * ex) ClientAbortException
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = [ClientAbortException::class])
    fun skipException(e: Exception): CommonResponse<Any> {
        log.warn(
            "[skipException] cause = {}, errorMsg = {}",
            NestedExceptionUtils.getMostSpecificCause(e),
            NestedExceptionUtils.getMostSpecificCause(e).message
        )
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR)
    }

    /**
     * 존재하지 않는 url method
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    fun requestMethodNotSupportedException(e: Exception): CommonResponse<Any> {
        log.warn(
            "[requestMethodNotSupportedException] cause = {}, errorMsg = {}",
            NestedExceptionUtils.getMostSpecificCause(e),
            NestedExceptionUtils.getMostSpecificCause(e).message
        )
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR)
    }

    /**
     * http status: 400 AND result: FAIL
     * request parameter 에러
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): CommonResponse<Any> {
        log.warn(
            "[BaseException] errorMsg = {}",
            NestedExceptionUtils.getMostSpecificCause(e).message
        )
        val bindingResult = e.bindingResult
        val fe = bindingResult.fieldError
        return if (fe != null) {
            val message = "Request Error" + " " + fe.field + "=" + fe.rejectedValue + " (" + fe.defaultMessage + ")"
            CommonResponse.fail(message, ErrorCode.COMMON_INVALID_PARAMETER.name)
        } else {
            CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER)
        }
    }
}