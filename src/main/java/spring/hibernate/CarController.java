package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.services.CarsServiceImpl;
import spring.services.EmployeesService;
import spring.services.EmployeesServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    }

    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        carsList = carsService.getAll();
        return new ModelAndView("all_cars_list", "carsList", carsList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        employeesList = employeesService.getAll();
        model.addAttribute("car", new Cars());
        model.addAttribute("employeesList", employeesList);
        return "add_car_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "car") Cars car
            , @ModelAttribute(value = "item.id") String itemId
            , @RequestParam(value = "date") String date) {
        int idAsInt = Integer.parseInt(itemId);
        Employees employeeToSet = employeesList.stream().filter(f -> f.getId() == idAsInt).findFirst().get();
        car.setEmployees(employeeToSet);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateParsed = LocalDate.parse(date, formatter);
        car.setRegistrationDate(dateParsed);

        if (car.getId() == 0) {
            addCarToDatabase(car);
        } else {
            updateCarInDatabase(car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        deleteCarFromDatabase(car);
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        return new ModelAndView("add_car_form", "car", car);
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
