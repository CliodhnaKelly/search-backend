package com.example.spider.search.dao;

import io.micrometer.core.instrument.Counter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SearchDao {

  @NonNull
  private final DataSource dataSource;

  private final Counter errorQueryingDb;

  public List<String> searchForLinks(String keyword){
    List<String> listOfUrls = new ArrayList<>();
    String query = "SELECT url FROM webpage WHERE raw_data LIKE '%" + keyword + "%';";
    try(Connection connection = dataSource.getConnection()) {
      try (PreparedStatement ps = connection.prepareStatement(query)) {

        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()) {
          String url = resultSet.getString("url");
          listOfUrls.add(url);
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
      errorQueryingDb.increment();
    }

    return listOfUrls;
  }

  public String searchForAd(String keyword){
    String query = "SELECT url FROM ads WHERE keyword LIKE '%" + keyword + "%';";
    String url ="";

    try(Connection connection = dataSource.getConnection()) {
      try (PreparedStatement ps = connection.prepareStatement(query)) {

        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
          url = resultSet.getString("url");
        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
      errorQueryingDb.increment();
    }

    return url;
  }
}
