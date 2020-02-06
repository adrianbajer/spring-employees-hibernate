package spring.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.services.CarsServiceImpl;
import spring.services.EmployeesServiceImpl;
import spring.services.PrintersServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private EmployeesServiceImpl employeesServiceImpl;
    @Autowired
    private CarsServiceImpl carsServiceImpl;
    @Autowired
    private PrintersServiceImpl printersServiceImpl;
    @Autowired
    private EntityManager entityManager;

    @RequestMapping("/")
    public String indexGet() {
        return "/index";
    }

    @Transactional
    @RequestMapping("/restoreData")
    public String onRestoreDatabaseButtonClick() {

        List<Printers> allPrinters = printersServiceImpl.getAll();
        for (Printers printers : allPrinters) {
            entityManager.persist(printers);
            entityManager.remove(printers);
        }

        List<Cars> allCars = carsServiceImpl.getAll();
        for (Cars cars : allCars) {
            entityManager.persist(cars);
            entityManager.remove(cars);
        }

        List<Employees> allEmployees = employeesServiceImpl.getAll();
        for (Employees employees : allEmployees) {
            entityManager.persist(employees);
            entityManager.remove(employees);
        }


        return "/index";
    }

}
