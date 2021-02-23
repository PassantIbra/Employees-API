package com.employees.challenge.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.EmployeeResDto;
import com.employees.challenge.model.Employee;
import com.employees.challenge.model.Employee.State;
import com.employees.challenge.repository.EmployeeRepository;

@Component
public class EmployeeServiceImp implements EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ModelMapper modelMapper;

	public EmployeeResDto save(AddEmployeeReqDto employeeDto) {
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		employee.setState(State.ADDED);
		Employee addedEmployee = employeeRepository.save(employee);
		return modelMapper.map(addedEmployee, EmployeeResDto.class);
	}

	public EmployeeResDto findById(int id) {
		// TODO Auto-generated method stub
		Optional<Employee> EmployeeToUpdate = employeeRepository.findById(id);
		if (EmployeeToUpdate.isPresent()) {

			return modelMapper.map(EmployeeToUpdate, EmployeeResDto.class);
		}
		return null;
	}

	public void updateEmployee(int id, State state) {
		employeeRepository.updateEmployeeState(id, state);
		return;

	}

	public EmployeeResDto findByEmail(String email) {
		// TODO Auto-generated method stub
		Optional<Employee> EmployeeToUpdate = employeeRepository.findByEmail(email);
		if (EmployeeToUpdate.isPresent()) {

			return modelMapper.map(EmployeeToUpdate, EmployeeResDto.class);
		}
		return null;
	}

}
