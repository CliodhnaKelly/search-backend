package com.example.spider.search.controller;

import com.example.spider.search.config.MicrometerConfig;
import com.example.spider.search.dao.SearchDao;
import java.util.List;

import com.example.spider.search.domain.Urls;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static java.util.Collections.singletonList;

@RequiredArgsConstructor
@RestController
public class Controller {

private final SearchDao searchDao;

private final Counter numberOfFailedSearches;
private final Counter numberOfSuccessfulSearches;

  @GetMapping("/search")
  public ResponseEntity<Urls> searchFor(@RequestParam String keyword) {

    // search database for keyword
    List<String> urls = searchDao.searchForLinks(keyword);
    if(urls.isEmpty()) {
      numberOfFailedSearches.increment();
    } else {
      numberOfSuccessfulSearches.increment();
    }

    String adUrl = searchDao.searchForAd(keyword);

    Urls urlResponse = new Urls();
    urlResponse.setSearchResults(urls);
    urlResponse.setAd(adUrl);

    HttpHeaders headers = new HttpHeaders();
    headers.put(HttpHeaders.CONTENT_TYPE, singletonList("application/json"));
    headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, singletonList("*"));

    return ResponseEntity.ok()
      .headers(headers)
      .body(urlResponse);
  }

}
