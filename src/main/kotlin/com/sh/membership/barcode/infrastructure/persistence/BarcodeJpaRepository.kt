package com.sh.membership.barcode.infrastructure.persistence

import com.sh.membership.barcode.domain.Barcode
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BarcodeJpaRepository : JpaRepository<Barcode, Int> {
    fun findByBarcode(barcode:String) : Optional<Barcode>
}