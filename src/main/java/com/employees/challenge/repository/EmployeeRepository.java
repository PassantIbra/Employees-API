package com.employees.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.employees.challenge.model.Employee;
import com.employees.challenge.model.Employee.State;;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	@Modifying
	@Transactional
	@Query("Update Employee e SET e.state=:state WHERE e.id=:id")
	public void updateEmployeeState(@Param("id") int id, @Param("state") State state);

	@Query("Select e from Employee e WHERE e.email=:email")
	public Optional<Employee> findByEmail(@Param("email") String email);

}
