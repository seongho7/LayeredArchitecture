package com.sh.membership.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Profile("test")
@Configuration
class H2Config {
    @Bean
    fun dataSource(): DataSource {
        val classPath = "db/migration/"
        val cpr = ClassPathResource(classPath)
        val sqlList = cpr.file.listFiles().map { "classpath:$classPath${it.name}" }

        return EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName("testdb;mode=MySQL")
            .addScripts(*sqlList.toTypedArray())
            .addScript("classpath:test-data.sql")
            .build()
    }
}