package com.ashish.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.ashish.model.NetflixTitles;

public interface NetflixTitlesRepositories extends CrudRepository<NetflixTitles, String> {
  //Select new FulfillOrder(fo.fulfillOrderId, fo.status) from FulfillOrder
  @Query(value = "select nt from NetflixTitles nt where nt.cast like %?1%",
      countQuery = "SELECT count(nt) from NetflixTitles nt where nt.cast like %?1%")
  Page<NetflixTitles> findByCast(String cast, Pageable pageable);
  
  @Query(value = "select nt from NetflixTitles nt where nt.director like %?1%",
      countQuery = "SELECT count(nt) from NetflixTitles nt where nt.director like %?1%")
  Page<NetflixTitles> findByDirector(String director, Pageable pageable);
  
  @Query(value = "select nt from NetflixTitles nt where nt.cast like %?1% AND nt.director like %?2%",
      countQuery = "SELECT count(nt) FROM NetflixTitles nt where nt.cast like %?1% AND nt.director like %?2%")
  Page<NetflixTitles> findByCastAndDirector(String cast, String director, Pageable pageable);
  
//  @Query(value = "select nt from NETFLIX_TITLES nt where nt.CAST like %:cast%")
//  Page<NetflixTitles> findByCastAndDirector(@Param("cast") String cast, Pageable pageable);
  
  Page<NetflixTitles> findAll(Pageable paging);
}
