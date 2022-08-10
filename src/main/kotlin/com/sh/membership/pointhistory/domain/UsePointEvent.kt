package com.sh.membership.pointhistory.domain

import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId
import java.time.LocalDateTime

data class UsePointEvent(
    val storeId: StoreId,
    val barcode:String,
    val currentPoint:Long,
    val useAmount:Long
) {
    fun toPointHistory(category:StoreCategory, storeName:String) : PointHistory {
        return PointHistory(
            barcode = barcode,
            type = ActionType.use,
            storeCategory = category,
            currentPoint = currentPoint,
            changedPoint = useAmount,
            storeName = storeName,
            approvedAt = LocalDateTime.now())
    }
}
