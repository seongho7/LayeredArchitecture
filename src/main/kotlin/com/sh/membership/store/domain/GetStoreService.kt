package com.sh.membership.store.domain

import java.util.*

class GetStoreService(
    private val loadStorePort: LoadStorePort
) : GetStoreQuery {
    override fun getStore(id: StoreId): Optional<Store> {
        return loadStorePort.loadById(id)
    }
}