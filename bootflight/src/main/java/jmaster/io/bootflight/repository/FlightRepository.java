package jmaster.io.bootflight.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jmaster.io.bootflight.entity.FlightEntity;

public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {
    //tao cac ham tim kiem theo ten thuoc tinh o class FlightEntity
    //tim chinh xac theo gia tri
    List<FlightEntity> findByFlightNo(String flightNo, Pageable pageable);
    
    List<FlightEntity> findByFlightDate(Date flightDate, Pageable pageable);

    List<FlightEntity> findByScheduledTime(String scheduledTime, Pageable pageable);

}
