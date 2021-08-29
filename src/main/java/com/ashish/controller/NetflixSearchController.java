package com.ashish.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.multipart.MultipartFile;

import com.ashish.service.CsvService;
import com.ashish.service.NetflixService;
import com.ashish.helper.CsvHelper;
import com.ashish.model.NetflixTitles;

//creating RestController  
@RestController  
public class NetflixSearchController   
{  

@Autowired
CsvService fileService;

@Autowired
NetflixService netFlixService;

@PostMapping("/uploadCsv")
public String uploadFile(@RequestParam("file") MultipartFile file) {
  String message = "";

  if (CsvHelper.hasCSVFormat(file)) {
    try {
      fileService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return message;
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return message;
    }
  }

  message = "Invalid file, Please upload a csv file!";
  return message;
}

@GetMapping("/netflixTitles")
public ResponseEntity<List<NetflixTitles>> getAllNetflixTitles() {
  try {
    List<NetflixTitles> netflixTitles = fileService.getAllNetflixTitles();

    if (netflixTitles.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(netflixTitles, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

@GetMapping("/search")
public ResponseEntity<Map<String, Object>> getAllNetflixTitlesWithPaginationAndSort(
    @RequestParam(required = false) String cast,
    @RequestParam(required = false) String director,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
  try {
    List<NetflixTitles> netflixTitles = new ArrayList<NetflixTitles>();
    Pageable paging = PageRequest.of(page, size);
    Page<NetflixTitles> pageNetflix;
    if (cast == null && director == null) {
      pageNetflix = netFlixService.getAllNetflixTitles(paging);
    }
    else if (cast == null && director != null) {
      pageNetflix = netFlixService.getAllNetflixTitlesWithDirector(director, paging);
    }
    else if(cast != null && director == null) {
      pageNetflix = netFlixService.getAllNetflixTitlesWithCast(cast, paging);
    }
    else {
      pageNetflix = netFlixService.getAllNetflixTitlesWithCastAndDirector(cast, director, paging);
    }
    netflixTitles = pageNetflix.getContent();
    
    Map<String, Object> response = new HashMap<>();
    response.put("netflixTitles", netflixTitles);
    response.put("currentPage", pageNetflix.getNumber());
    response.put("totalItems", pageNetflix.getTotalElements());
    response.put("totalPages", pageNetflix.getTotalPages());
    
    return new ResponseEntity<>(response, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    
  }
}

}  