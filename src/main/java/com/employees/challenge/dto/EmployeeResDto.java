package com.employees.challenge.dto;

import com.employees.challenge.model.Employee.State;

public class EmployeeResDto {
	private int id;
	private State state;
	private String name;
	private int age;
	private String adress;
	private String contractInfo;
	private String email;
	private String phone;

	public EmployeeResDto() {
		super();
	}

	public EmployeeResDto(int id, State state, String name, int age, String adress, String contractInfo, String email,
			String phone) {
		super();
		this.id = id;
		this.state = state;
		this.name = name;
		this.age = age;
		this.adress = adress;
		this.contractInfo = contractInfo;
		this.email = email;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
