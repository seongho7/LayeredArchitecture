package com.sh.membership.barcode.infrastructure

import com.sh.membership.barcode.domain.*
import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import com.sh.membership.barcode.infrastructure.persistence.BarcodePersistenceAdapter
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import java.util.concurrent.ThreadLocalRandom

class ThreadLocalBarcodeGenerator(
    private val barcodePersistenceAdapter: BarcodePersistenceAdapter
) : BarcodeGenerator {
    private val log = LoggerFactory.getLogger(ThreadLocalBarcodeGenerator::class.java)
    override fun generate(command: IssueBarcodeCommand): Barcode? {
        repeat(5) {
            val barcodeValue = ThreadLocalRandom.current().nextLong(
                BarcodeGenerator.MIN_VALUE,
                BarcodeGenerator.RANDOM_MAX_VALUE
            )
            val optionalBarcode = barcodePersistenceAdapter.loadByBarcode(barcodeValue.toString())
            if(optionalBarcode.isEmpty) {
                val barcode = Barcode(userId = command.userId, barcode = barcodeValue.toString())
                try {
                    barcodePersistenceAdapter.save(barcode)
                    return barcode
                }catch (e:DataIntegrityViolationException){
                    log.debug("바코드 생성 값 중복:", e)
                }
            }
        }
        return null
    }
}