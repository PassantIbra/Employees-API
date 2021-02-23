package com.employees.challenge.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.employees.challenge.model.Employee.State;

public class UpdateEmployeReqDto {
	@Enumerated(EnumType.STRING)
	@NotNull(message = "please specify employee state")
	private State state;
	@NotNull(message = "please specify employee id")
	@Min(1)
	private int id;

	public UpdateEmployeReqDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateEmployeReqDto(@NotNull(message = "please specify employee state") State state,
			@NotNull(message = "please specify employee id") @Min(1) int id) {
		super();
		this.state = state;
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
