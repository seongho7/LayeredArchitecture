package com.sh.membership.store.domain

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Embeddable
data class StoreId (
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,
) : Serializable