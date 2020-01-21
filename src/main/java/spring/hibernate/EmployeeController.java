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
public class EmployeeController {
    //@Autowired musi być zakomentowane żeby kontroler przełączał się na listę kiedy baza jest niedostępna
    private EmployeeDao employeeDao;
    private List<Employees> list;

    public EmployeeController() {
        try {
            employeeDao = new EmployeeDao();
            list = employeeDao.getEmployees();
        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();
            list = new ArrayList<>();
            Employees employee1 = new Employees("Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
            Employees employee2 = new Employees("Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
            Employees employee3 = new Employees("Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
            list.addAll(Arrays.asList(employee1, employee2, employee3));
        }
    }

    //metoda działa i z listą i z bazą danych
    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    //metoda działa i z listą i z bazą danych
    @RequestMapping(value = "/empform", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("employee", new Employees());
        return "emp/add_employee_form";
    }

    //metoda działa tylko z bazą danych
    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "employee") Employees employee) {
        if (employee.getId() == 0) {
            employeeDao.saveEmployee(employee);
        } else {
            employeeDao.updateEmployees(employee);
        }
        return new ModelAndView("redirect:/viewemp");
    }

    //metoda działa tylko z bazą danych
    @RequestMapping(value = "/delete_emp", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        employeeDao.deleteEmployees(employee);
        return new ModelAndView("redirect:/viewemp");
    }

    //metoda działa i z listą i z bazą danych
    @RequestMapping(value = "/edit_emp", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        return new ModelAndView("emp/add_employee_form", "employee", employee);
    }

    // jeśli usunę odwołanie do employeeDao w tej metodzie, to nie będzie się updatowała lista pracowników na stronie
    // głównej przy dodaniu kolejnego pracownika. więc metoda nie działa z listą
    @RequestMapping("/viewemp")
    public ModelAndView viewemp(Model model) {
        List<Employees> list = employeeDao.getEmployees();
        return new ModelAndView("emp/all_employees_list", "list", list);
    }

    //metoda działa i z listą i z bazą danych
    private Employees getEmployeesById(@RequestParam int id) {
        //List<Employees> list = employeeDao.getEmployees();
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
