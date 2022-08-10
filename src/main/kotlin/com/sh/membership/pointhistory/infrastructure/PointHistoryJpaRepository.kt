package com.sh.membership.pointhistory.infrastructure

import com.sh.membership.pointhistory.domain.PointHistory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PointHistoryJpaRepository : JpaRepository<PointHistory, Long> {
    fun findByBarcodeAndApprovedAtBetween(barcode:String, approvedAtStart:LocalDateTime, approvedAtEnd:LocalDateTime, pageable: Pageable) : List<PointHistory>
}