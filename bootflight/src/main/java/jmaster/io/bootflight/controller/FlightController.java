package jmaster.io.bootflight.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jmaster.io.bootflight.entity.FlightEntity;
import jmaster.io.bootflight.repository.FlightRepository;

@Controller
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    @GetMapping({ "/flights", "/" })
    public String flights(@RequestParam(name = "flightNo", required = false) String flightNo, Model model) {
	List<FlightEntity> flightEntities = new ArrayList<FlightEntity>();
	// lay ra 20 ban ghi moi nhat theo truong createdDate
	PageRequest pageable = PageRequest.of(0, 20, Sort.by("createdDate").descending());

	if (flightNo != null) {
	    // tim theo flight no
	    flightEntities = flightRepository.findByFlightNo(flightNo, pageable);
	} else {
	    flightEntities = flightRepository.findAll(pageable).getContent();
	}

	// day qua view gia dien danh sach chuyen bay
	model.addAttribute("flights", flightEntities);
	return "flights";
    }
}
