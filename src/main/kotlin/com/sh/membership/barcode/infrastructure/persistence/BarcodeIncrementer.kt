package com.sh.membership.barcode.infrastructure.persistence

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class BarcodeIncrementer{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0
}