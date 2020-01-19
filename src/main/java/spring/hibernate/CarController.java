package spring.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarsDao carsDao;
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping("/seeAll")
    public ModelAndView showCarList(Model model) {
        List<Cars> list = carsDao.getCars();
        return new ModelAndView("emp/all_cars_list", "list", list);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        List<Employees> employeesList = employeeDao.getEmployees();
        model.addAttribute("car", new Cars());
        model.addAttribute("employeesList", employeesList);
        return "emp/add_car_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "car") Cars car, @RequestParam(value = "employees.id") String employeesId) {
//            Integer employeeId = employee.getId();
//            List<Employees> list = employeeDao.getEmployees();
//            Employees employeeToSet = list.stream().filter(f -> f.getId() == employeeId).findFirst().get();
//            car.setEmployees(employeeToSet);
        if (car.getId() == 0) {
            carsDao.saveCar(car);
        } else {
            carsDao.updateCars(car);
        }
        return new ModelAndView("redirect:/car/seeAll");
    }

    private Cars getCarById(@RequestParam int id) {
        List<Cars> list = carsDao.getCars();
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
