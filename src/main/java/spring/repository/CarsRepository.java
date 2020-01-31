package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import spring.hibernate.Cars;

public interface CarsRepository extends CrudRepository<Cars, Long> {
}
