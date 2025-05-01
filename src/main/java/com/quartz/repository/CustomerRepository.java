package com.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quartz.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{


}
