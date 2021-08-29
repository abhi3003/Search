package com.ashish.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ashish.helper.CsvHelper;
import com.ashish.model.NetflixTitles;
import com.ashish.repository.NetflixTitlesRepositories;

@Service
public class NetflixService {
  @Autowired
  NetflixTitlesRepositories repository;
  
  public void save(MultipartFile file) {
    try {
      List<NetflixTitles> netflixTitles = CsvHelper.csvToNetflixTitles(file.getInputStream());
      repository.saveAll(netflixTitles);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }
  
  public Page<NetflixTitles> getAllNetflixTitles(Pageable paging) {
    return repository.findAll(paging);
  }
  
  public Page<NetflixTitles> getAllNetflixTitlesWithCast(String cast, Pageable paging) {
    return repository.findByCast(cast, paging);
  }
  
  public Page<NetflixTitles> getAllNetflixTitlesWithDirector(String director, Pageable paging) {
    return repository.findByDirector(director, paging);
  }
  
  public Page<NetflixTitles> getAllNetflixTitlesWithCastAndDirector(String cast, String director, Pageable paging) {
    return repository.findByCastAndDirector(cast, director, paging);
  }
}
