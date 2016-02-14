package com.github.swwjf.config

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.{ConfigurationProperties, EnableConfigurationProperties}
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.{Bean, Configuration, Primary}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = Array(DBConfig.RootPackage))
@EnableTransactionManagement
@EnableConfigurationProperties(Array(classOf[JpaProperties]))
private class DBConfig {
  @Bean
  @ConfigurationProperties(prefix = "jdbc.datasource.ws")
  @Primary
  def wsDataSource(): DataSource =
    DataSourceBuilder
      .create()
      .`type`(classOf[BasicDataSource])
      .build()

  @Bean
  @Autowired
  def entityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean =
    builder
      .dataSource(wsDataSource())
      .persistenceUnit("ws")
      .packages(DBConfig.RootPackage)
      .build()

  @Bean
  def transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager = {
    val txManager = new JpaTransactionManager()
    txManager.setEntityManagerFactory(entityManagerFactory)
    txManager
  }
}

private object DBConfig extends DBConstants

private sealed trait DBConstants {
  final val RootPackage = "com.github.swwjf"
}
