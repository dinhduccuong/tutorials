package jmaster.io.bootflight.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jmaster.io.bootflight.entity.FlightEntity;
import jmaster.io.bootflight.repository.FlightRepository;

@Service
public class FlightClientService {
    @Autowired
    private FlightRepository flightRepository;

    @Scheduled(fixedRate = 60000) // mili giay
    public void fetchFlightData() {
	getFlightInfo();
	// tao ra nhieu ham getFlightInfo... ten khac nhau de goi trong nay
	// neu ma nhu cau can lay cac airport khac nhau cung luc
    }

    public String getAccessToken(String airport) {
	RestTemplate restTemplate = new RestTemplate();

	final String USER = "";// (cung cấp riêng)
	final String KEY = "";// (cung cấp riêng)

	String authURL = "https://web.sags.vn/FlightAPI/Authenticate?user=" + USER + "&key=" + KEY + "&airport="
		+ airport;

	ResponseEntity<String> response = restTemplate.getForEntity(authURL, String.class);

	if (response.getStatusCode() == HttpStatus.OK) {
	    try {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());

		/*
		 * { "Token": "sample string 1", "Born": "Datetime", "Die": "Datetime" }
		 */

		return root.path("Token").textValue();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	return null;
    }

    public void getFlightInfo() {
	RestTemplate restTemplate = new RestTemplate();

	final String AIRPORT = "SGN";// cảng hàng không muốn lấy dữ liệu: “SGN” hoặc “DAD” hoặc “CXR”
	final String ARRDEP = "A";// Đến/Đi, giá trị: A: đến - D: đi - All: tất cả
	final String FLIGHTNO = "All"; // Số hiệu chuyến bay. “All” cho tất cả

	SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
	String date = sdf.format(new Date()); // “ddMMMyyyy” Ngày lấy chuyến bay

	String resourceURL = "https://web.sags.vn/FlightAPI/Schedule?date=" + date + "&arrdep=" + ARRDEP + "&flightno="
		+ FLIGHTNO;

	// lay access token
	String accessToken = getAccessToken(AIRPORT);
	if (accessToken == null)
	    return;// ket thuc

	HttpHeaders headers = new HttpHeaders();
	headers.set("Authorization", accessToken);
	headers.set("Airport", AIRPORT);

	ResponseEntity<FlightEntity[]> response = restTemplate.exchange(resourceURL, HttpMethod.GET,
		new HttpEntity(headers), FlightEntity[].class);

	FlightEntity[] lightEntities = response.getBody();
	for (FlightEntity flightEntity : lightEntities)
	    flightRepository.save(flightEntity);
    }
}
