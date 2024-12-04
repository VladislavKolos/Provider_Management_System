package org.example.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource

@Configuration
class TransactionalAndJpaConfig(
    private val env: Environment
) {

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().apply {
            dataSource = dataSource()
            setPackagesToScan("org.example.model")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaProperties(hibernateProperties())
        }
    }

    @Bean
    fun dataSource(): DataSource {
        return DriverManagerDataSource().apply {
            env.getProperty("spring.datasource.driver-class-name")?.let { setDriverClassName(it) }
            url = env.getProperty("spring.datasource.url")
            username = env.getProperty("spring.datasource.username")
            password = env.getProperty("spring.datasource.password")
        }
    }

    @Bean
    fun transactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): JpaTransactionManager {
        return JpaTransactionManager().apply {
            setEntityManagerFactory(entityManagerFactory.`object`)
        }
    }

    private fun hibernateProperties(): Properties {
        return Properties().apply {
            put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"))
            put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"))
            put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"))
        }
    }
}