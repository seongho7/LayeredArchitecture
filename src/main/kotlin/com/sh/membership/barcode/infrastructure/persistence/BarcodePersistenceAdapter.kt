package com.sh.membership.barcode.infrastructure.persistence

import com.sh.membership.barcode.domain.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class BarcodePersistenceAdapter(
    private val barcodeJpaRepository: BarcodeJpaRepository
) : SaveBarcodePort, LoadBarcodePort {
    override fun save(barcode: Barcode): Barcode {
        return barcodeJpaRepository.save(barcode)
    }

    override fun loadByUserId(userId: Int): Optional<Barcode> {
        return barcodeJpaRepository.findById(userId)
    }

    override fun loadByBarcode(barcode: String): Optional<Barcode> {
        return barcodeJpaRepository.findByBarcode(barcode)
    }
}