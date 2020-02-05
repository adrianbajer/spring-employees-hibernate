package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.repository.CarsRepository;
import spring.repository.EmployeesRepository;
import spring.services.CarsServiceImpl;
import spring.services.EmployeesService;
import spring.services.EmployeesServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {
    private List<Cars> carsList;
    private List<Employees> employeesList;
    private CarsServiceImpl carsService;
    private EmployeesService employeesService;

    //Grupa 1. Ma za zadanie klasę dodać klasę, która będzie obsługiwała przypisane do pracownika drukarki z adnotacją
    // @ManyToMany oraz dorobić do niej odpowiedni formularz.
    //Dodaje przycisk który umożliwi przywrócenie bazy danych do punktu początkowego oraz wystawia aplikacje na heroku.
    // Grupa 2. Przepina projekt na jparepository/crudrepository oraz tworzy zdalną bazę danych, którą podpina do projektu.
    // Czas do 9.02. Sposób oddania to wysłanie linku do wspólnego repozytorium oraz linku do działającej aplikacji

    public CarController(CarsServiceImpl carsService ,EmployeesServiceImpl employeesService) {

        this.carsService = carsService;
        this.employeesService = employeesService;

        carsList = carsService.getAll();
        employeesList = employeesService.getAll();

//        try {
////            hibernateDao = new HibernateDao();
////            DataSource.supplyDatabase();
////            employeesList = hibernateDao.get(Employees.class);
////            carsList = hibernateDao.get(Cars.class);
//        } catch (NullPointerException exception) {
//            System.out.println("No connection with database");
//            exception.getMessage();
//
//            employeesList = new ArrayList<>();
//            Employees employee1 = new Employees(1, "Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
//            Employees employee2 = new Employees(2, "Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
//            Employees employee3 = new Employees(3, "Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
//            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));
//
//            carsList = new ArrayList<>();
//            Cars car1 = new Cars(employee1, 1, "Fiat", "126p", new Date());
//            Cars car2 = new Cars(employee2, 2, "Honda", "Civic", new Date());
//            carsList.addAll(Arrays.asList(car1, car2));
//        }
    }

    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        carsList = carsService.getAll();
        return new ModelAndView("/all_cars_list", "carsList", carsList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        employeesList = employeesService.getAll();
        model.addAttribute("car", new Cars());
        model.addAttribute("employeesList", employeesList);
        return "/add_car_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "car") Cars car
            , @ModelAttribute(value = "item.id") String itemId
            , @RequestParam(value = "date") String date) {
        int idAsInt = Integer.parseInt(itemId);
        Employees employeeToSet = employeesList.stream().filter(f -> f.getId() == idAsInt).findFirst().get();
        car.setEmployees(employeeToSet);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateParsed = format.parse(date);
            car.setRegistrationDate(dateParsed);
        } catch (ParseException e) {
            e.printStackTrace();
            car.setRegistrationDate(new Date());
        }

        if (car.getId() == 0) {
            addCarToDatabase(car);
//            car.setId(carsList.size());
//            carsList.add(car);
        } else {
            updateCarInDatabase(car);
//            carsList.set(car.getId() - 1, car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        deleteCarFromDatabase(car);
//        carsList.remove(car);
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        return new ModelAndView("/add_car_form", "car", car);
    }

    private Cars getCarById(@RequestParam int id) {
        return carsList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void addCarToDatabase(Cars car) {
        try {
            carsService.create(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updateCarInDatabase(Cars car) {
        try {
            carsService.create(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deleteCarFromDatabase(Cars car) {
        try {
            carsService.delete(car);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
