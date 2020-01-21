package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {
    //@Autowired
    private CarsDao carsDao;
    private List<Cars> carsList;
    //@Autowired
    private EmployeeDao employeeDao;
    private List<Employees> employeesList;

    public CarController() {
        try {
            employeeDao = new EmployeeDao();
            employeesList = employeeDao.getEmployees();
            carsDao = new CarsDao();
            carsList = carsDao.getCars();

        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();

            employeesList = new ArrayList<>();
            Employees employee1 = new Employees("Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
            Employees employee2 = new Employees("Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
            Employees employee3 = new Employees("Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));

            carsList = new ArrayList<>();
            Cars car1 = new Cars("Fiat", "126p", new Date());
            Cars car2 = new Cars("Honda", "Civic", new Date());
            carsList.addAll(Arrays.asList(car1, car2));
        }
    }

    // jeśli usunę odwołanie do employeeDao w tej metodzie, to nie będzie się updatowała lista pracowników na stronie
    // głównej przy dodaniu kolejnego pracownika. więc metoda nie działa z listą
    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        List<Cars> list = carsDao.getCars();
        return new ModelAndView("emp/all_cars_list", "carsList", carsList);
    }

    //metoda działa i z listą i z bazą danych
    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        //List<Employees> employeesList = employeeDao.getEmployees();
        model.addAttribute("car", new Cars());
        model.addAttribute("employeesList", employeesList);
        return "emp/add_car_form";
    }

    // metoda działa tylko z bazą

    //zakomentowana część kodu, która miała pobierać id employee wybranego w dropdown list. Nie udało mi się
    // sprawić, żeby ta funkcjonalność działała
    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "car") Cars car
//                            , @RequestParam(value = "employees") Employees employees
    ) {
//            Integer employeeId = employees.getId();
//            List<Employees> list = employeeDao.getEmployees();
//            Employees employeeToSet = list.stream().filter(f -> f.getId() == employeeId).findFirst().get();
//            car.setEmployees(employeeToSet);
        List<Employees> list = employeeDao.getEmployees();
        Employees employees = list.get(0);
        car.setEmployees(employees); //musi być jakieś istniejący employee żeby samochód zapisał się do bazy

        if (car.getId() == 0) {
            carsDao.saveCar(car);
        } else {
            carsDao.updateCars(car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    //metoda działa i z listą i z bazą danych
    private Cars getCarById(@RequestParam int id) {
        //List<Cars> list = carsDao.getCars();
        return carsList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
