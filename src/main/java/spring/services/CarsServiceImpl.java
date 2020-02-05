package spring.services;

import org.springframework.stereotype.Service;
import spring.hibernate.Cars;
import spring.repository.CarsRepository;

import java.util.List;

@Service
public class CarsServiceImpl implements CarsService{

    private CarsRepository carsRepository;

    public CarsServiceImpl(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    @Override
    public void create(Cars cars) {
        carsRepository.save(cars);
    }

    @Override
    public List<Cars> getAll() {
        return carsRepository.findAll();
    }

    @Override
    public void update(Cars cars) {
        carsRepository.save(cars);
    }

    @Override
    public void delete(Cars cars) {
        carsRepository.delete(cars);
    }
}
