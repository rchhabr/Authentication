package com.example.demo.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	
	@PostMapping("/employee")
	public String saveEmployee(@RequestBody Employee employee) throws InterruptedException, ExecutionException {
		return employeeService.saveEmployee(employee);
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable String id) throws InterruptedException, ExecutionException {
		return employeeService.getEmployeeDetails(id);
	}
	
	@DeleteMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable String id) throws InterruptedException, ExecutionException {
		return employeeService.deleteEmployee(id);
	}
	 
	@GetMapping("/employeeAll")
	public List<Employee> getEmployee() throws InterruptedException, ExecutionException {
		return employeeService.getAllEmployeeDetails();
	}
	
	@PutMapping("/employee")
	public String updateEmployee(@RequestBody Employee employee) throws InterruptedException, ExecutionException {
		return employeeService.updateEmployee(employee);
	}
}
