package com.employees.challenge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.EmployeeResDto;
import com.employees.challenge.dto.UpdateEmployeReqDto;
import com.employees.challenge.exceptionhandling.DuplicateEmailException;
import com.employees.challenge.exceptionhandling.EmployeeNotFoundException;
import com.employees.challenge.model.Employee.State;
import com.employees.challenge.service.EmployeeService;

@RestController
public class EmployeesController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private KafkaTemplate<String, AddEmployeeReqDto> addEmployeekafkaTemplate;
	@Autowired
	private KafkaTemplate<String, UpdateEmployeReqDto> updateEmployeekafkaTemplate;

	private static final String TOPIC1 = "add_employee";
	private static final String TOPIC2 = "update_employee";

	@PostMapping(value = "/employees")
	public ResponseEntity<AddEmployeeReqDto> addEmployee(@Valid @RequestBody AddEmployeeReqDto addEmployeeReqDto) {
		EmployeeResDto savedEmployee = null;
		savedEmployee = employeeService.findByEmail(addEmployeeReqDto.getEmail());
		if (savedEmployee != null) {
			throw new DuplicateEmailException("email already exists");
		}

		addEmployeekafkaTemplate.send(TOPIC1, addEmployeeReqDto);
		addEmployeeReqDto.setState(State.ADDED);
		return new ResponseEntity<AddEmployeeReqDto>(addEmployeeReqDto, HttpStatus.CREATED);

	}

	@PutMapping(value = "/employees")
	public ResponseEntity updateEmployeeState(@Valid @RequestBody UpdateEmployeReqDto updateEmployeReqDto)
			throws EmployeeNotFoundException {

		EmployeeResDto employeeToUPdate = employeeService.findById(updateEmployeReqDto.getId());
		if (employeeToUPdate == null) {
			throw new EmployeeNotFoundException("Employee not found for this id : " + updateEmployeReqDto.getId());
		}
		updateEmployeekafkaTemplate.send(TOPIC2, updateEmployeReqDto);
		return new ResponseEntity<Object>(HttpStatus.OK);

	}

}
