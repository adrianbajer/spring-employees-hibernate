package spring.services;

import spring.hibernate.Cars;

import java.util.List;

public interface CarsService {

    void create(Cars cars);

    List<Cars> getAll();

    void update(Cars cars);

    void delete(Cars cars);
}
