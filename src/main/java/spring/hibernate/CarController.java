package spring.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    @Autowired
    private HibernateDao hibernateDao;

    public CarController() {
        try {
            hibernateDao = new HibernateDao();
            DataSource.supplyDatabase();
            employeesList = hibernateDao.get(Employees.class);
            carsList = hibernateDao.get(Cars.class);
        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();

            employeesList = new ArrayList<>();
            Employees employee1 = new Employees(1, "Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
            Employees employee2 = new Employees(2, "Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
            Employees employee3 = new Employees(3, "Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));

            carsList = new ArrayList<>();
            Cars car1 = new Cars(employee1, "Fiat", "126p", new Date());
            Cars car2 = new Cars(employee2, "Honda", "Civic", new Date());
            carsList.addAll(Arrays.asList(car1, car2));


        }
    }

    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        //List<Cars> carsList = hibernateDao.get(Cars.class);
        return new ModelAndView("emp/all_cars_list", "carsList", carsList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("car", new Cars());
        model.addAttribute("employeeToSet", new Employees());
        model.addAttribute("employeesList", employeesList);
        return "emp/add_car_form";
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
            hibernateDao.saveEntity(car);
        } else {
            hibernateDao.updateEntity(car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    //    //metoda działa tylko z bazą danych
    @RequestMapping(value = "/delete_car", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        hibernateDao.deleteEntity(car);
        return new ModelAndView("redirect:/car/seeAll");
    }

    @RequestMapping(value = "/edit_car", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "car_id") String car_id) {
        Cars car = getCarById(Integer.parseInt(car_id));
        return new ModelAndView("emp/add_car_form", "car", car);
    }

    private Cars getCarById(@RequestParam int id) {
        //List<Cars> list = hibernateDao.get(Cars.class);
        return carsList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
