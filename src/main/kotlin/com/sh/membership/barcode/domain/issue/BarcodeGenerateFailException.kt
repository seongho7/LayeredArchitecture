package com.sh.membership.barcode.domain.issue

import com.sh.membership.common.exception.BaseException
import com.sh.membership.common.response.ErrorCode

class BarcodeGenerateFailException(message:String = ErrorCode.BARCODE_GENERATE_FAIL.errorMsg)
    : BaseException(errorCode = ErrorCode.BARCODE_GENERATE_FAIL, message = message ) {
}