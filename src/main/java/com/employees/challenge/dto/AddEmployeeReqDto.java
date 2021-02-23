package com.employees.challenge.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.employees.challenge.model.Employee.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class AddEmployeeReqDto {
	@NotBlank(message = "please specify employee name")
	private String name;
	private int age;
	private String adress;
	@NotNull(message = "please specify contractInfo")
	@Column(name = "contract_info")
	private String contractInfo;
	@NotBlank
	@Column(unique = true)
	@Email(message = "a valid email is mandatory")
	private String email;
	@Pattern(regexp = "^01[0-2]{1}[0-9]{8}", message = "a valid 11 digit Egyption cell phone number is mandatory EX:01234567890")
	private String phone;
	@JsonProperty(access = Access.READ_ONLY)
	private State state;

	public AddEmployeeReqDto() {
		super();
	}

	public AddEmployeeReqDto(@NotBlank(message = "please specify employee name") String name, int age, String adress,
			@NotNull(message = "please specify contractInfo") String contractInfo,
			@NotBlank @Email(message = "a valid email is mandatory") String email,
			@Pattern(regexp = "^01[0-2]{1}[0-9]{8}", message = "a valid 11 digit Egyption cell phone number is mandatory EX:01234567890") String phone,
			@JsonProperty(access = Access.WRITE_ONLY) State state) {
		super();
		this.name = name;
		this.age = age;
		this.adress = adress;
		this.contractInfo = contractInfo;
		this.email = email;
		this.phone = phone;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
