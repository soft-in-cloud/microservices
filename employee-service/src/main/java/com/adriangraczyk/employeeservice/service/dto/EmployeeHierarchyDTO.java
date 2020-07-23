package com.adriangraczyk.employeeservice.service.dto;


import java.util.List;
import java.util.Objects;

public class EmployeeHierarchyDTO {

    private EmployeeDTO manager;
    private List<EmployeeDTO> workers;

    public EmployeeHierarchyDTO() {
    }

    public EmployeeHierarchyDTO(EmployeeDTO manager, List<EmployeeDTO> workers) {
        this.manager = manager;
        this.workers = workers;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public List<EmployeeDTO> getWorkers() {
        return workers;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public void setWorkers(List<EmployeeDTO> workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeHierarchyDTO that = (EmployeeHierarchyDTO) o;
        return Objects.equals(manager, that.manager) &&
            Objects.equals(workers, that.workers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager, workers);
    }

    @Override
    public String toString() {
        return "EmployeeHierarchyDTO{" +
            "boss=" + manager +
            ", workers=" + workers +
            '}';
    }
}
