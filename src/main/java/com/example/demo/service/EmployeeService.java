package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeRepository;

@Transactional
@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository emprep;
	
	public List<Employee> getAllEmployees() {
		return emprep.findAll();
	}
	
	public Employee get(Long eid) {
        return emprep.findById(eid).get();
    	}

	public Employee getByName(String ename) {
        	return emprep.getEmployeeByName(ename);
    	}

	public Employee save(Employee emp) {
		return emprep.save(emp);
	}

	public void delete(Long eid) {
		emprep.deleteById(eid);
	}

	public Employee update(Employee emp) {  
        return emprep.save(emp);
    }  

	
	
}
