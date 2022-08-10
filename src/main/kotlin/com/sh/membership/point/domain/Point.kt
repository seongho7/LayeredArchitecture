package com.sh.membership.point.domain

import com.sh.membership.point.domain.use.PointNotEnoughException
import com.sh.membership.store.domain.StoreCategory
import org.hibernate.envers.Audited
import java.time.LocalDateTime
import javax.persistence.*

@Audited
@Table(name = "user_point", indexes = [Index(columnList = "barcode, category", unique = true)])
@Entity
class Point(
    @Id
    val id:Long = 0,
    @Column(length = 12, nullable = false)
    val barcode:String,
    @Column(length = 3, nullable = false)
    @Enumerated(EnumType.STRING)
    val category: StoreCategory,
    @Column(nullable = false)
    val createdAt:LocalDateTime = LocalDateTime.now()
) {

    var point:Long = 0
        private set

    @Version
    val version:Long = 0

    fun collectPoint(pointAmount:Long) {
        this.point += pointAmount
    }

    private fun isUsablePoint(pointAmount: Long) = this.point >= pointAmount

    fun usePoint(pointAmount: Long) {
        if(isUsablePoint(pointAmount).not()) {
            throw PointNotEnoughException()
        }
        this.point = this.point - pointAmount
    }
}