package com.matt.service;



import com.matt.modal.Employee;
import com.matt.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl
	implements EmployeeServices {

	@Autowired private EmployeeRepository empRepo;

	
	public List<Employee> getAllEmployee()
	{
	
		return empRepo.findAll();
	
	}

	@Override public void save(Employee employee)
	{
		empRepo.save(employee);
	}

	@Override public Employee getById(Long id)
	{
		Optional<Employee> optional = empRepo.findById(id);
		Employee employee = null;
		if (optional.isPresent())
			employee = optional.get();
		else
			throw new RuntimeException(
				"Employee not found for id : " + id);
		return employee;
	}

	@Override public void deleteViaId(long id)
	{
		empRepo.deleteById(id);
	}

	@Override
	public List<Employee> getAllEmployee(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
	//find by keyword
	public List<Employee> findByKeyword(String keyword){
		return empRepo.findByKeyword(keyword);
		
	}

	
	
}
