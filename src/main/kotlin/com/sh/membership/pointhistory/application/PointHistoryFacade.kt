package com.sh.membership.pointhistory.application

import com.sh.membership.barcode.domain.validate.BarcodeValidator
import com.sh.membership.pointhistory.domain.PointHistory
import com.sh.membership.pointhistory.infrastructure.PointHistoryJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PointHistoryFacade(
    private val jpaRepository: PointHistoryJpaRepository,
    private val barcodeValidator: BarcodeValidator
) {
    fun retrieve(barcode:String, approvedAtStart: LocalDate, approvedAtEnd: LocalDate, pageable: Pageable) : List<PointHistory> {
        barcodeValidator.validate(barcode)
        return jpaRepository.findByBarcodeAndApprovedAtBetween(
            barcode = barcode,
            approvedAtStart = approvedAtStart.atTime(0,0),
            approvedAtEnd = approvedAtEnd.plusDays(1).atTime(0,0),
            pageable = pageable
        )
    }
}