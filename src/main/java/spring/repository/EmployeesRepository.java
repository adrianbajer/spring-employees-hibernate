package spring.repository;

import org.springframework.data.repository.CrudRepository;
import spring.hibernate.Employees;

import java.util.List;

public interface EmployeesRepository extends CrudRepository<Employees, Integer> {

    @Override
    List<Employees> findAll();
}
