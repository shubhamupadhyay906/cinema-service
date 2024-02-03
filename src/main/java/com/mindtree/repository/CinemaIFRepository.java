package com.mindtree.repository;

import com.mindtree.model.CinemaIF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaIFRepository extends JpaRepository<CinemaIF, Long> {

    List<CinemaIF> findByCinemaName(String cinemaName);

}
