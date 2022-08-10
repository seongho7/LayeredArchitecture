package com.sh.membership.barcode.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface BarcodeIncrementerJpaRepository : JpaRepository<BarcodeIncrementer, Long> {
}