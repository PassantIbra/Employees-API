package com.employees.challenge.service;

import javax.validation.Valid;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.EmployeeResDto;
import com.employees.challenge.model.Employee.State;

public interface EmployeeService {
	public EmployeeResDto save(@Valid AddEmployeeReqDto employee);

	public EmployeeResDto findById(int id);

	public EmployeeResDto findByEmail(String email);

	public void updateEmployee(int id, State state);

}
