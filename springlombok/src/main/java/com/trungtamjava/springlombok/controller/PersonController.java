package com.trungtamjava.springlombok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trungtamjava.springlombok.model.Person;

@Controller
public class PersonController {

	
	@GetMapping("/person")
	public String getPerson(Model model) {
		Person p = new Person(1, "Java Master");
		model.addAttribute("p", p);
		return "person";
	}
}
