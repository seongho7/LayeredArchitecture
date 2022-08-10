package com.sh.membership.point.domain.collect

import com.sh.membership.store.domain.StoreId

data class CollectPointEvent(
    val storeId: StoreId,
    val barcode:String,
    val currentPoint:Long,
    val collectAmount:Long
) {
    constructor(command:CollectPointCommand, currentPoint:Long)
            : this(storeId = command.storeId, barcode = command.barcode, collectAmount = command.pointAmount, currentPoint = currentPoint)
}