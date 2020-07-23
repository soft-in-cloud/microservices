package com.adriangraczyk.employeeservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.adriangraczyk.employeeservice.domain.enumeration.Role;

/**
 * A DTO for the {@link com.adriangraczyk.employeeservice.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {
    
    private String id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Min(value = 1)
    @Max(value = 200)
    private Integer age;

    @NotNull
    @Pattern(regexp = "[0-9]{11}")
    private String pesel;

    @NotNull
    private Role role;


    private String managerId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String employeeId) {
        this.managerId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        return id != null && id.equals(((EmployeeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", age=" + getAge() +
            ", pesel='" + getPesel() + "'" +
            ", role='" + getRole() + "'" +
            ", managerId='" + getManagerId() + "'" +
            "}";
    }
}
