package com.sh.membership.barcode.domain

import java.time.LocalDateTime
import javax.persistence.*


@Table(name = "user_barcode", indexes = [Index(columnList = "barcode", unique = true)])
@Entity
class Barcode(
    @Id
    val userId:Int,
    @Column(length = 10, nullable = false)
    val barcode:String,
    @Column(nullable = false)
    val createdAt:LocalDateTime = LocalDateTime.now()
) {

}