package com.sh.membership.barcode.infrastructure

import com.sh.membership.barcode.domain.Barcode
import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import com.sh.membership.barcode.infrastructure.persistence.BarcodeIncrementer
import com.sh.membership.barcode.infrastructure.persistence.BarcodeIncrementerJpaRepository
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Order(Ordered.LOWEST_PRECEDENCE)
class IncrementBarcodeGenerator(
    private val barcodeIncrementerJpaRepository: BarcodeIncrementerJpaRepository
) : BarcodeGenerator {
    override fun generate(command: IssueBarcodeCommand): Barcode {
        val barcodeIncrementer = barcodeIncrementerJpaRepository.save(BarcodeIncrementer())
        return Barcode(command.userId, barcodeIncrementer.id.toString())
    }
}