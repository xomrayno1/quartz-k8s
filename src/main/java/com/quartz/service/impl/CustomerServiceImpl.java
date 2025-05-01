package com.quartz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quartz.entity.Customer;
import com.quartz.repository.CustomerRepository;
import com.quartz.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerRepository customerRepository;

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer findById(Long id) {
		return customerRepository.findById(id).orElse(null);
	}

}
