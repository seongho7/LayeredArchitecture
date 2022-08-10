package com.sh.membership.point.domain

import com.sh.membership.common.exception.BaseException
import com.sh.membership.common.response.ErrorCode

class StoreNotFoundException(message:String = ErrorCode.STORE_NOT_FOUND.errorMsg) : BaseException(ErrorCode.STORE_NOT_FOUND, message) {
}