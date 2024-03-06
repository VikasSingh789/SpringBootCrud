package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService empser;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return empser.getAllEmployees();
	}
	
	@GetMapping("/employee/{eid}")
	public Employee get(@PathVariable Long eid) {
		return empser.get(eid);
	}

	@GetMapping("/employee/getEmployeeByName/{ename}")
	public Employee getByEmployeeName(@PathVariable String ename) {
		return empser.getByName(ename);
	}
	
	@PostMapping("/employee")
	public Employee save(@RequestBody Employee emp) {
		return empser.save(emp);
	}
	
	@DeleteMapping("/employee/delete/{eid}")
	public void delete(@PathVariable Long eid) {
		empser.delete(eid);
	}
	
	@PutMapping("/employee/update/{eid}")  
    public Employee update(@RequestBody Employee emp,@PathVariable Long eid) {  
        emp.setEid(eid);
        return empser.update(emp);
    }
	
}
