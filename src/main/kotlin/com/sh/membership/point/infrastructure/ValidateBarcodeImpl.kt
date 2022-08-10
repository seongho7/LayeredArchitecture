package com.sh.membership.point.infrastructure

import com.sh.membership.barcode.domain.validate.BarcodeValidator
import com.sh.membership.point.domain.ValidateBarcode
import org.springframework.stereotype.Component

@Component
class ValidateBarcodeImpl(
    private val barcodeValidator: BarcodeValidator
) : ValidateBarcode {
    override fun validate(barcode: String) {
        barcodeValidator.validate(barcode)
    }
}