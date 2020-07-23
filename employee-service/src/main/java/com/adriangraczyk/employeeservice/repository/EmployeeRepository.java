package com.adriangraczyk.employeeservice.repository;

import com.adriangraczyk.employeeservice.domain.Employee;

import com.adriangraczyk.employeeservice.domain.enumeration.Role;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    List<Employee> findAllByManagerId(String managerId);

    boolean existsByPesel(String pesel);

    Long countByManagerId(String managerId);

    Long countByRole(Role role);
}
