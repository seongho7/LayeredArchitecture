package com.sh.membership.point.domain.collect

import com.sh.membership.point.domain.Point
import com.sh.membership.store.domain.StoreId

interface CollectPointUseCase{
    fun collectPoint(command: CollectPointCommand) : Point
}

data class CollectPointCommand(
    val storeId: StoreId,
    val barcode:String,
    val pointAmount:Long
)