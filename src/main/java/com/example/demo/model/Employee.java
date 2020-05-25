package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employee")
public class Employee {
	
	public Employee(){
		System.out.println("constructor 3");
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eid;
	
	@Column(name="ename")
	private String ename;
	
	@Column(name="salary")
	private Integer salary;
	
	@Column(name="address")
	private String address;
	
	public Employee(Long eid, String ename, Integer salary, String address){
		this.eid = eid;
		this.address = address;
		this.ename = ename;
		this.salary = salary;
	}
	
	public Long getEid() {
		System.out.println(eid);
		return eid;
	}
	public void setEid(Long eid) {
		this.eid = eid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename; 
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", address=" + address + "] vikas";
	}
	
}
