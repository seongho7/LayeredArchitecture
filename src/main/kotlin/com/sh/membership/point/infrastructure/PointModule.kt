package com.sh.membership.point.infrastructure

import com.sh.membership.common.id.IdGenerator
import com.sh.membership.point.domain.LoadStorePort
import com.sh.membership.point.domain.use.UsePointService
import com.sh.membership.point.domain.ValidateBarcode
import com.sh.membership.point.domain.collect.CollectPointService
import com.sh.membership.point.domain.collect.CollectPointUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.support.RetryTemplate
import org.springframework.retry.support.RetryTemplateBuilder

@Configuration
class PointModule {
    @Bean
    fun collectPointService(pointPersistenceAdapter: PointPersistenceAdapter,
                            loadStorePort: LoadStorePort,
                            validateBarcode: ValidateBarcode,
                            idGenerator: IdGenerator) : CollectPointUseCase {
        return CollectPointService(
            loadPointPort = pointPersistenceAdapter,
            savePointPort = pointPersistenceAdapter,
            loadStorePort = loadStorePort,
            validateBarcode = validateBarcode,
            idGenerator = idGenerator)
    }

    @Bean
    fun usePointService(
        pointPersistenceAdapter: PointPersistenceAdapter,
        loadStorePort: LoadStorePort,
        validateBarcode: ValidateBarcode,
    ) : UsePointService {
        return UsePointService(
            loadPointPort = pointPersistenceAdapter,
            savePointPort = pointPersistenceAdapter,
            loadStorePort = loadStorePort,
            validateBarcode = validateBarcode
        )
    }

    @Bean
    fun pointModifyRetryTemplate() : RetryTemplate {
        return RetryTemplateBuilder()
            .retryOn(ObjectOptimisticLockingFailureException::class.java)
            .retryOn(DataIntegrityViolationException::class.java)
            .maxAttempts(3)
            .fixedBackoff(1000)
            .build()
    }
}