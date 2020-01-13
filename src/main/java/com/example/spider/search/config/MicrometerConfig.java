package com.example.spider.search.config;

import com.example.spider.search.controller.Controller;
import com.example.spider.search.dao.SearchDao;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfig {

  @Bean
  public Counter numberOfSuccessfulSearches(MeterRegistry meterRegistry) {
    return meterRegistry.counter(Controller.class.getPackage().getName() + ".numberOfSuccessfulSearches");
  }

  @Bean
  public Counter numberOfFailedSearches(MeterRegistry meterRegistry) {
    return meterRegistry.counter(Controller.class.getPackage().getName() + ".numberOfFailedSearches");
  }

  @Bean
  public Counter errorQueryingDb(MeterRegistry meterRegistry) {
    return meterRegistry.counter(SearchDao.class.getPackage().getName() + ".numberOfErrorsQueryingDb");
  }
}


