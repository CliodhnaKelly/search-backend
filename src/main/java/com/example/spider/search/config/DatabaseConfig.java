package com.example.spider.search.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

  @NonNull
  private final DatabaseProperties databaseProperties;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(org.postgresql.Driver.class.getName());
    config.setJdbcUrl(databaseProperties.getUrl());
    config.setUsername(databaseProperties.getUsername());
    config.setPassword(databaseProperties.getPassword());
    config.setSchema("spiderdb");
    return new HikariDataSource(config);
  }
}
