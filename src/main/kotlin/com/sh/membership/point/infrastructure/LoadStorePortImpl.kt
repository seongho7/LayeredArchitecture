package com.sh.membership.point.infrastructure

import com.sh.membership.point.domain.LoadStorePort
import com.sh.membership.point.domain.StoreNotFoundException
import com.sh.membership.store.domain.GetStoreQuery
import com.sh.membership.store.domain.Store
import com.sh.membership.store.domain.StoreId
import org.springframework.stereotype.Component

@Component
class LoadStorePortImpl(
    private val getStoreQuery: GetStoreQuery
) : LoadStorePort {
    override fun loadStore(storeId: StoreId): Store {
        return getStoreQuery.getStore(storeId).orElseThrow { StoreNotFoundException() }
    }
}