package com.sh.membership.point.domain.use

import com.sh.membership.common.exception.BaseException
import com.sh.membership.common.response.ErrorCode

class PointNotEnoughException(message:String = ErrorCode.POINT_NOT_ENOUGH.errorMsg) : BaseException(ErrorCode.POINT_NOT_ENOUGH, message) {
}