package com.sh.membership.point.infrastructure

import com.sh.membership.point.domain.Point
import com.sh.membership.store.domain.StoreCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*
import javax.persistence.LockModeType

interface PointJpaRepository : JpaRepository<Point, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    fun findByBarcodeAndCategory(barcode:String, category: StoreCategory) : Optional<Point>
}