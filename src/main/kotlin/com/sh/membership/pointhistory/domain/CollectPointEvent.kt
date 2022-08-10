package com.sh.membership.pointhistory.domain

import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId
import java.time.LocalDateTime

data class CollectPointEvent(
    val storeId: StoreId,
    val barcode:String,
    val currentPoint:Long,
    val collectAmount:Long
) {
    fun toPointHistory(category:StoreCategory, storeName:String) : PointHistory {
        return PointHistory(
            barcode = barcode,
            type = ActionType.collect,
            storeCategory = category,
            currentPoint = currentPoint,
            changedPoint = collectAmount,
            storeName = storeName,
            approvedAt = LocalDateTime.now())
    }
}