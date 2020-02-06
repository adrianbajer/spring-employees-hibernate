package spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.hibernate.Employees;
import spring.hibernate.Printers;
import spring.repository.EmployeesRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService{

    private EmployeesRepository employeesRepository;

    @Autowired
    private EntityManager entityManager;

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
        employees = entityManager.find(Employees.class, employees.getId());
        for (Printers printers : employees.getPrintersSet()) {
            if (printers.getEmployeesSet().size() == 1) {
                entityManager.remove(printers);
            } else {
                printers.getEmployeesSet().remove(employees);
            }
        }
        employeesRepository.delete(employees);
    }
}
