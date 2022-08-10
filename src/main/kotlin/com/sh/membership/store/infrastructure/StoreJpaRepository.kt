package com.sh.membership.store.infrastructure

import com.sh.membership.store.domain.Store
import com.sh.membership.store.domain.StoreId
import org.springframework.data.jpa.repository.JpaRepository

interface StoreJpaRepository : JpaRepository<Store, StoreId>  {
}