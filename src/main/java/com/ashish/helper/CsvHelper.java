package com.ashish.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import com.ashish.model.NetflixTitles;
public class CsvHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<NetflixTitles> csvToNetflixTitles(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<NetflixTitles> netTitles = new ArrayList<NetflixTitles>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        NetflixTitles netflixTitles = new NetflixTitles(
              csvRecord.get("show_id"),
              csvRecord.get("type"),
              csvRecord.get("title"),
              csvRecord.get("director"),
              csvRecord.get("cast"),
              csvRecord.get("country"),
              csvRecord.get("date_added"),
              csvRecord.get("release_year"),
              csvRecord.get("rating"),
              csvRecord.get("duration"),
              csvRecord.get("listed_in"),
              csvRecord.get("description") 
            );

        netTitles.add(netflixTitles);
      }

      return netTitles;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
}
