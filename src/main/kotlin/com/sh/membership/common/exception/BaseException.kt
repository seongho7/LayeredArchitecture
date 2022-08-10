package com.sh.membership.common.exception

import com.sh.membership.common.response.ErrorCode

open class BaseException(val errorCode: ErrorCode, message: String) : RuntimeException(message) {
}
