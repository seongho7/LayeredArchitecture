package com.sh.membership.point.infrastructure

import com.sh.membership.point.domain.LoadPointPort
import com.sh.membership.point.domain.Point
import com.sh.membership.point.domain.SavePointPort
import com.sh.membership.store.domain.StoreCategory
import org.springframework.stereotype.Component
import java.util.*

@Component
class PointPersistenceAdapter(
    private val pointJpaRepository: PointJpaRepository
) : SavePointPort, LoadPointPort {
    override fun save(point: Point): Point {
        return pointJpaRepository.save(point)
    }

    override fun loadPoint(barcode: String, category: StoreCategory): Optional<Point> {
        return pointJpaRepository.findByBarcodeAndCategory(barcode, category)
    }
}