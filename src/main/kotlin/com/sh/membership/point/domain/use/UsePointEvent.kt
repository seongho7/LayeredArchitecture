package com.sh.membership.point.domain.use

import com.sh.membership.store.domain.StoreId

data class UsePointEvent(
    val storeId: StoreId,
    val barcode:String,
    val currentPoint:Long,
    val useAmount:Long
) {
    constructor(command:UsePointCommand, currentPoint:Long)
            : this(storeId = command.storeId, barcode = command.barcode, useAmount = command.pointAmount, currentPoint = currentPoint)
}
