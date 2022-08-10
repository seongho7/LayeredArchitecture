package com.sh.membership.store.domain

import java.util.*

interface GetStoreQuery {
    fun getStore(id: StoreId) : Optional<Store>
}