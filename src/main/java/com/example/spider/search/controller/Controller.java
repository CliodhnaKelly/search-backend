package com.example.spider.search.controller;

import com.example.spider.search.dao.SearchDao;
import com.example.spider.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

@RequiredArgsConstructor
@RestController
public class Controller {

private final SearchDao searchDao;

  @GetMapping("/search")
  public ResponseEntity<List<String>> searchFor(@RequestParam String keyword) {

    // search database for keyword
    List<String> urls = searchDao.searchForLinks(keyword);

    HttpHeaders headers = new HttpHeaders();
    headers.put(HttpHeaders.CONTENT_TYPE, singletonList("application/json"));
    headers.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, singletonList("*"));

    return ResponseEntity.ok()
      .headers(headers)
      .body(urls);
  }

}
