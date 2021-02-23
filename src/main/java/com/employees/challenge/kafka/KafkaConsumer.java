package com.employees.challenge.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.employees.challenge.dto.AddEmployeeReqDto;
import com.employees.challenge.dto.UpdateEmployeReqDto;
import com.employees.challenge.service.EmployeeService;

@Service
public class KafkaConsumer {
	@Autowired
	private EmployeeService employeeService;

	// handle add employee request
	@KafkaListener(topics = "add_employee", groupId = "add_employee", containerFactory = "addEmployeeListenerFactory")
	public void AddEmployeeconsumer(AddEmployeeReqDto addEmployeeReqDto) {
		employeeService.save(addEmployeeReqDto);

	}

	// handle update employee request
	@KafkaListener(topics = "update_employee", groupId = "update_employee", containerFactory = "updateEmployeeListenerFactory")
	public void updateEmployeeConsumer(UpdateEmployeReqDto updateEmployeReqDto) {
		employeeService.updateEmployee(updateEmployeReqDto.getId(), updateEmployeReqDto.getState());

	}

}
