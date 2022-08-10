package com.sh.membership.barcode.application

import com.sh.membership.barcode.domain.Barcode
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import com.sh.membership.barcode.domain.issue.IssueBarcodeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
class BarcodeFacade(
    private val issueBarcodeUseCase: IssueBarcodeUseCase,
    private val transactionTemplate: TransactionTemplate,
) {

    fun issueBarcode(command: IssueBarcodeCommand) : Barcode {
        val barcode = transactionTemplate.execute<Barcode> {
            issueBarcodeUseCase.issueBarcode(command)
        }!!

        return barcode
    }
}