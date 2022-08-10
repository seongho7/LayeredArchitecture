package com.sh.membership.store.infrastructure

import com.sh.membership.store.domain.LoadStorePort
import com.sh.membership.store.domain.Store
import com.sh.membership.store.domain.StoreId
import org.springframework.stereotype.Component
import java.util.*

@Component
class StorePersistenceAdapter(
    private val storeJpaRepository: StoreJpaRepository
) : LoadStorePort {
    override fun loadById(id: StoreId): Optional<Store> {
        return storeJpaRepository.findById(id)
    }
}