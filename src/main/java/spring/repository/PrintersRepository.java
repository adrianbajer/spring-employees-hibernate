package spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spring.hibernate.Cars;
import spring.hibernate.Printers;

import java.util.List;

@Repository
public interface PrintersRepository extends CrudRepository<Printers, Long> {

    @Override
    List<Printers> findAll();
}
