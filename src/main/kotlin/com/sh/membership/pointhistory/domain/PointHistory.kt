package com.sh.membership.pointhistory.domain

import com.sh.membership.store.domain.StoreCategory
import java.time.LocalDateTime
import javax.persistence.*

@Table(indexes = [Index(columnList = "barcode, approvedAt DESC")])
@Entity
class PointHistory(
    @Column(length = 10, nullable = false)
    val barcode:String,
    @Column(length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    val type:ActionType,
    val currentPoint:Long,
    val changedPoint:Long,
    @Column(length = 8, nullable = false)
    @Enumerated(EnumType.STRING)
    val storeCategory: StoreCategory,
    @Column(length = 32, nullable = false)
    val storeName:String,
    @Column(nullable = false)
    val approvedAt:LocalDateTime
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long = 0
}

enum class ActionType {
    use, collect
}