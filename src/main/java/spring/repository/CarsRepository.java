package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.Cars;
import spring.hibernate.Employees;

import java.util.List;

@Repository
public interface CarsRepository extends CrudRepository<Cars, Long> {

    @Override
    List<Cars> findAll();
}
