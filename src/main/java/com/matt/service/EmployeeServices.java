package com.matt.service;

import java.util.List;

import com.matt.modal.Employee;

public interface EmployeeServices {
	List<Employee> getAllEmployee(String keyword);
	void save(Employee employee);
	Employee getById(Long id);
	void deleteViaId(long id);
	
	
	
}

