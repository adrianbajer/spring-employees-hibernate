package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.model.Cars;
import spring.model.Employees;
import spring.model.Printers;
import spring.services.CarsServiceImpl;
import spring.services.EmployeesServiceImpl;
import spring.services.PrintersServiceImpl;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return "index";
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

        // restoring database works, but ids wont reset, they start "over" the ones that was already used

        Employees employees1 = new Employees("Piotr", "Pawlak", "Grójecka 28", "Warszawa",
                1000, 18, LocalDate.of(2015, 6, 12), 0, "dolek@wp.pl");
        Employees employees2 = new Employees("Paweł", "Kaczyński", "Zielona 28", "Kraków",
                2000, 28, LocalDate.of(2012, 3, 1), 1, "ek@wp.pl");
        Employees employees3 = new Employees("Anna", "Pawlak", "Grójecka 28", "Warszawa",
                3000, 45, LocalDate.of(2019, 12, 12), 1, "zlek@wp.pl");
        Employees employees4 = new Employees("Katarzyna", "Gierszałt", "Marymoncka 28", "Gdynia",
                4000, 39, LocalDate.of(2015, 6, 12), 1, "tek@wp.pl");
        Employees employees5 = new Employees("Maciej", "Józefowicz", "Koszykowa 28", "Warszawa",
                5000, 31, LocalDate.of(2019, 12, 12), 1, "plek@wp.pl");
        Employees employees6 = new Employees("Genowefa", "Pigwa", "Rybickiego 128", "Zamość",
                6000, 29, LocalDate.of(2019, 12, 12), 1, "tlek@wp.pl");
        Employees employees7 = new Employees("Piotr", "Złomczyński", "Szucha 8", "Warszawa",
                1500, 18, LocalDate.of(2019, 12, 12), 0, "aat@wp.pl");


        List<Employees> employeesList = Arrays.asList(employees1, employees2, employees3, employees4, employees5, employees6, employees7);
        for (Employees employee : employeesList) {
            entityManager.persist(employee);
        }

        Cars car1 = new Cars(employees6, "Subaru", "Forester", LocalDate.of(2015, 6, 12));
        Cars car2 = new Cars(employees2, "Ford", "Fiesta", LocalDate.of(2016, 6, 12));
        Cars car3 = new Cars(employees3, "Fiat", "126jpa", LocalDate.of(2017, 6, 12));
        Cars car4 = new Cars(employees4, "Subaru", "Forester", LocalDate.of(2018, 6, 12));
        Cars car5 = new Cars(employees5, "Subaru", "Forester", LocalDate.of(2019, 6, 12));

        List<Cars> carsList = Arrays.asList(car1, car2, car3, car4, car5);
        for (Cars car : carsList) {
            entityManager.persist(car);
        }

        Set<Employees> setForPrinter1 = new HashSet<>(Arrays.asList(employees1, employees2, employees3, employees4, employees5, employees6, employees7));
        Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employees1, employees6, employees7));

        Printers printer1 = new Printers(setForPrinter1, "Hewlett Packard", "1234h", true, true);
        Printers printer2 = new Printers("EasyJet", "asd", true, false);
        Printers printer3 = new Printers(setForPrinter2, "Optimus", "Prime", false, false);

        List<Printers> printersList = Arrays.asList(printer1, printer2, printer3);
        for (Printers printer : printersList) {
            entityManager.persist(printer);
        }

        return "index";
    }

}
