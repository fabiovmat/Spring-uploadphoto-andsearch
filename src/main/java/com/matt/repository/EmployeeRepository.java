package com.matt.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.matt.modal.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

	@Query(value = "SELECT * FROM Employee p WHERE p.name LIKE %:keyword%", nativeQuery=true)
	public List<Employee> findByKeyword(@Param("keyword")String keyword);
 //or e.sobrenome LIKE %:keyword%--depois
}

