package com.sh.membership.barcode.domain

import java.util.*

interface LoadBarcodePort {
    fun loadByUserId(userId: Int) : Optional<Barcode>
    fun loadByBarcode(barcode:String) : Optional<Barcode>
}

interface SaveBarcodePort {
    fun save(barcode: Barcode) : Barcode
}