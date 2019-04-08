package com.employee.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@Controller
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeerepository;

	@GetMapping("/employee")
	public List<Employee> getAllEmployee() {
		return employeerepository.findAll();
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long employeeid)
			throws ResourceNotFoundException {

		Employee employee = employeerepository.findById(employeeid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeid));
		return ResponseEntity.ok().body(employee);

	}

	@PostMapping("/employee")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {

		return employeerepository.save(employee);

	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") long empoyeeid,@Valid @RequestBody Employee employeedetails)
			throws ResourceNotFoundException {
		Employee employee = employeerepository.findById(empoyeeid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" + empoyeeid));

		employee.setEmailId(employeedetails.getEmailId());
		employee.setLastname(employeedetails.getLastname());
		employee.setFirstname(employeedetails.getFirstname());
		final Employee employee2 = employeerepository.save(employee);
		return ResponseEntity.ok(employee2);

	}
	
	@DeleteMapping("/employees/{id}")
	 public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
	   throws ResourceNotFoundException {
	  Employee employee = employeerepository.findById(employeeId)
	    .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
	  employeerepository.delete(employee);
	  Map<String, Boolean> response = new HashMap<>();
	  response.put("deleted", Boolean.TRUE);
	  return response;
	 }

}
