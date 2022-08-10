package com.sh.membership.store.domain

import java.util.*

interface LoadStorePort {
    fun loadById(id:StoreId) : Optional<Store>
}