package com.sh.membership.barcode.domain.issue

import com.sh.membership.barcode.domain.Barcode

interface BarcodeGenerator {
    companion object {
        const val MIN_VALUE = 1_000_000_000L
        const val RANDOM_MAX_VALUE = 5_000_000_000L
        const val MAX_VALUE = 9_999_999_999L
    }

    fun generate(command: IssueBarcodeCommand) : Barcode?
}