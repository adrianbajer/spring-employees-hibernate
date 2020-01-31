package spring.repository;

import org.springframework.data.repository.CrudRepository;
import spring.hibernate.Employees;

public interface EmployeesRepository extends CrudRepository<Employees, Long> {
}
