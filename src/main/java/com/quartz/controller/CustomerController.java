package com.quartz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quartz.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(customerService.findAll());
	}
	

}
