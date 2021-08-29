package com.ashish.repository;

import com.ashish.model.NetflixTitles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetflixTitlesCsvRepositories extends JpaRepository<NetflixTitles, String> {

}
