package spring.services;

import spring.hibernate.Employees;

import java.util.List;

public interface EmployeesService {

    void create(Employees employees);

    List<Employees> getAll();

    void update(Employees employees);

    void delete(Employees employees);

}
