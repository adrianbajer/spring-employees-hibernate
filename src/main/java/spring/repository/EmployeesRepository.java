package spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.model.Employees;

import java.util.List;

@Repository
public interface EmployeesRepository extends CrudRepository<Employees, Long> {

    @Override
    List<Employees> findAll();
}
