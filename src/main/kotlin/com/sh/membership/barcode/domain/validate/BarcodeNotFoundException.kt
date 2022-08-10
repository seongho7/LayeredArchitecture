package com.sh.membership.barcode.domain.validate

import com.sh.membership.common.exception.BaseException
import com.sh.membership.common.response.ErrorCode

class BarcodeNotFoundException(message:String = ErrorCode.BARCODE_NOT_FOUND.errorMsg) : BaseException(ErrorCode.BARCODE_NOT_FOUND, message){
}