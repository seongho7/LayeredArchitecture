package com.sh.membership.store.domain

import javax.persistence.*

@Entity
class Store(
    @EmbeddedId
    val id:StoreId,
    @Column(nullable = false, length = 32)
    val name:String,
    @Column(length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    val category:StoreCategory
) {
}

enum class StoreCategory(
    val desc:String
) {
    A("식품"), B("화장품"), C("식당")
}