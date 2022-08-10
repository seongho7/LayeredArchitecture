package com.sh.membership.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Configuration
class DatabaseConfig {

    @Bean
    fun transactionTemplate(platformTransactionManager: PlatformTransactionManager) : TransactionTemplate {
        return TransactionTemplate(platformTransactionManager)
    }
}