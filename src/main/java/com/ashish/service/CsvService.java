package com.ashish.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashish.helper.CsvHelper;
import com.ashish.model.NetflixTitles;
import com.ashish.repository.NetflixTitlesCsvRepositories;

@Service
public class CsvService {
  @Autowired
  NetflixTitlesCsvRepositories repository;
  
  public void save(MultipartFile file) {
    try {
      List<NetflixTitles> netflixTitles = CsvHelper.csvToNetflixTitles(file.getInputStream());
      repository.saveAll(netflixTitles);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }
  
  public List<NetflixTitles> getAllNetflixTitles() {
    return repository.findAll();
  }
}
