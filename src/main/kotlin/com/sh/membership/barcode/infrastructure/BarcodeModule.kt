package com.sh.membership.barcode.infrastructure

import com.sh.membership.barcode.domain.*
import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.domain.issue.IssueBarcodeService
import com.sh.membership.barcode.domain.validate.BarcodeValidatorImpl
import com.sh.membership.barcode.infrastructure.persistence.BarcodeIncrementerJpaRepository
import com.sh.membership.barcode.infrastructure.persistence.BarcodePersistenceAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BarcodeModule {

    @Bean
    fun issueBarcodeService(barcodeGenerators:List<BarcodeGenerator>,
                            loadBarcodePort: LoadBarcodePort) : IssueBarcodeService {
        return IssueBarcodeService(barcodeGenerators, loadBarcodePort)
    }

    @Bean
    fun barcodeGenerators(barcodePersistenceAdapter: BarcodePersistenceAdapter,
                          barcodeIncrementerJpaRepository: BarcodeIncrementerJpaRepository
    ) : List<BarcodeGenerator> {
        return listOf(
            ThreadLocalBarcodeGenerator(barcodePersistenceAdapter),
            IncrementBarcodeGenerator(barcodeIncrementerJpaRepository)
        )
    }

    @Bean
    fun validateBarcodeService(loadBarcodePort: LoadBarcodePort) = BarcodeValidatorImpl(loadBarcodePort)

}