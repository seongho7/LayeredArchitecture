package com.sh.membership.point.domain.use

import com.sh.membership.point.domain.Point
import com.sh.membership.store.domain.StoreId

interface UsePointUseCase{
    fun usePoint(command: UsePointCommand) : Point
}

data class UsePointCommand(
    val storeId: StoreId,
    val barcode:String,
    val pointAmount:Long
)