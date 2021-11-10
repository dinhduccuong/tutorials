package jmaster.io.bootflight.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "flight_info")
@Data
public class FlightEntity {
    @Id
    private int flightId;
    private String flightNo;
    private Date flightDate;
    private String scheduledTime;
    private String estimatedTime;
    private String actualTime;
    private String chockTime;
    private String route;
    private String arrDep;
    private Date opsFlightDateTime;
    private String acRegNo;
    private String acType;
    private String flightType;
    private String natureOfFlight;
    private String parkingBay;
    private String gate;
    private Date createdDate;

    public FlightEntity() {
	createdDate = new Date();
    }

}
