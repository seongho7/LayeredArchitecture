package com.sh.membership.barcode.domain.validate

import com.sh.membership.barcode.domain.LoadBarcodePort

class BarcodeValidatorImpl(
    private val loadBarcodePort: LoadBarcodePort
) : BarcodeValidator {
    override fun validate(barcode: String) {
        val oBarcode = loadBarcodePort.loadByBarcode(barcode)
        if(oBarcode.isEmpty) {
            throw BarcodeNotFoundException()
        }
    }
}