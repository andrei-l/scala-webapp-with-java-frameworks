package com.github.swwjf.config

import javax.sql.DataSource

import com.github.springtestdbunit.bean.{DatabaseConfigBean, DatabaseDataSourceConnectionFactoryBean}
import org.dbunit.database.DatabaseDataSourceConnection
import org.dbunit.ext.h2.H2DataTypeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
private class WSDBTestConfig {
  @Bean
  def databaseConfig(): DatabaseConfigBean = {
    val dbConfig = new DatabaseConfigBean()
    dbConfig.setDatatypeFactory(new H2DataTypeFactory())
    dbConfig
  }

  @Bean
  @Autowired
  def wsDBUnitDatabaseConnection(dataSource: DataSource, databaseConfigBean: DatabaseConfigBean): DatabaseDataSourceConnection = {
    val connectionFactoryBean: DatabaseDataSourceConnectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean
    connectionFactoryBean.setDataSource(dataSource)
    connectionFactoryBean.setDatabaseConfig(databaseConfigBean)
    connectionFactoryBean.getObject
  }
}

private[swwjf] object WSDBTestConfig {
  final val ConnectionName = "wsDBUnitDatabaseConnection"
}
