package com.sh.membership.point.domain

import com.sh.membership.store.domain.Store
import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId
import java.util.*

interface SavePointPort{
    fun save(point: Point) : Point
}

interface LoadPointPort {
    fun loadPoint(barcode:String, category: StoreCategory) : Optional<Point>
}

interface LoadStorePort{
    fun loadStore(storeId: StoreId) : Store
}