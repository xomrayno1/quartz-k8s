package com.quartz.service;

import java.util.List;

import com.quartz.entity.Customer;

public interface CustomerService {
	
	List<Customer> findAll();
	
	Customer save(Customer customer);
	
	Customer findById(Long id);

}
