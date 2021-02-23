package com.employees.challenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank(message = "please specify employee name")
	private String name;
	@Enumerated(EnumType.STRING)
	private State state;
	private int age;
	private String adress;
	@NotNull(message = "please specify contractInfo")
	@Column(name = "contract_info")
	private String contractInfo;
	@NotBlank
	@Column(unique = true)
	@Email(message = "a valid email is mandatory")
	private String email;
	@Length(max = 11)
	@Pattern(regexp = "^01[0-2]{1}[0-9]{8}", message = "a valid 11 digit Egyption cell phone number is mandatory EX:01234567890")
	private String phone;

	public Employee() {
	}

	public Employee(Integer id, @NotBlank(message = "please specify employee name") String name, State state, int age,
			String adress, @NotNull(message = "please specify contractInfo") String contractInfo,
			@NotBlank @Email(message = "a valid email is mandatory") String email,
			@Pattern(regexp = "^01[0-2]{1}[0-9]{8}", message = "a valid 11 digit Egyption cell phone number is mandatory EX:01234567890") String phone) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.age = age;
		this.adress = adress;
		this.contractInfo = contractInfo;
		this.email = email;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	public enum State {
		ADDED, INCHECK, APPROVED, ACTIVE

	}
}
