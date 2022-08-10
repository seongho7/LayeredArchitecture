package com.sh.membership.barcode.domain.issue

import com.sh.membership.barcode.domain.Barcode

interface IssueBarcodeUseCase {
    fun issueBarcode(command: IssueBarcodeCommand) : Barcode
}

data class IssueBarcodeCommand(
    val userId:Int
)