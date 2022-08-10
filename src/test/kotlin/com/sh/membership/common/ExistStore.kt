package com.sh.membership.common

import com.sh.membership.store.domain.Store
import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId

object ExistStore {
    val A = Store(StoreId(1L), "카오 식품", StoreCategory.A)
    val B = Store(StoreId(2L), "카페 화장품", StoreCategory.B)
    val C = Store(StoreId(3L), "카카 식당", StoreCategory.C)
}