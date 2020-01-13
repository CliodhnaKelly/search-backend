package com.example.spider.search.domain;

import lombok.Data;

import java.util.List;

@Data
public class Urls {
  private List<String> searchResults;
  private String ad;
}
