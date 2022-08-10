package com.sh.membership.barcode.domain.issue

import com.sh.membership.barcode.domain.Barcode
import com.sh.membership.barcode.domain.LoadBarcodePort

class IssueBarcodeService(
    private val barcodeGenerators: List<BarcodeGenerator>,
    private val loadBarcodePort: LoadBarcodePort
) : IssueBarcodeUseCase {
    override fun issueBarcode(command: IssueBarcodeCommand): Barcode {
        val oBarcode = loadBarcodePort.loadByUserId(command.userId)
        if(oBarcode.isPresent) {
            return oBarcode.get()
        }
        barcodeGenerators.forEach {
            val barcode = it.generate(command)
            if(barcode != null) return barcode
        }
        throw BarcodeGenerateFailException()
    }
}