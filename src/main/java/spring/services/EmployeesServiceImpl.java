package spring.services;

import org.springframework.stereotype.Service;
import spring.hibernate.Employees;
import spring.repository.EmployeesRepository;

import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService{

    private EmployeesRepository employeesRepository;

    public EmployeesServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public void create(Employees employees){
        employeesRepository.save(employees);
    }

    public List<Employees> getAll(){
        return employeesRepository.findAll();
    }

    public void update (Employees employees){
        employeesRepository.save(employees);
    }

    public void delete(Employees employees){
        employeesRepository.delete(employees);
    }
}
