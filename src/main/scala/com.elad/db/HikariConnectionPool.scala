package com.elad.db

import com.zaxxer.hikari
import com.zaxxer.hikari.HikariDataSource

object HikariConnectionPool {

  private val dbUrl = sys.env("DB_URL")
  private val dbDriver = sys.env("DB_DRIVER")
  private val dbUser = sys.env("DB_USER")
  private val dbPassword = sys.env("DB_PASSWORD")

  private val conf = new hikari.HikariConfig()

  conf.setJdbcUrl(dbUrl)
  conf.setDriverClassName(dbDriver)
  conf.setUsername(dbUser)
  conf.setPassword(dbPassword)

  private val dataSource = new HikariDataSource(conf)

  def getConnection() = dataSource.getConnection()

}