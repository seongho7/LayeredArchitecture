package com.sh.membership.store.infrastructure

import com.sh.membership.store.domain.GetStoreQuery
import com.sh.membership.store.domain.GetStoreService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StoreModule {
    @Bean
    fun getStoreService(storePersistenceAdapter: StorePersistenceAdapter) : GetStoreQuery {
        return GetStoreService(storePersistenceAdapter)
    }
}