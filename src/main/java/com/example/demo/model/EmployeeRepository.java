package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query(value = "select * from employee where ename= ?1", nativeQuery = true)
	public Employee getEmployeeByName(String ename);
	
}
