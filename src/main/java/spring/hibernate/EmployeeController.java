package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.services.EmployeesServiceImpl;

import java.util.List;

@Controller
public class EmployeeController {
    private List<Employees> employeesList;
    private EmployeesServiceImpl employeesServiceImpl;




    public EmployeeController(EmployeesServiceImpl employeesServiceImpl) {

        this.employeesServiceImpl = employeesServiceImpl;
        employeesList = employeesServiceImpl.getAll();

//        employeesList = employeesService.getAll();
//        System.out.println(employeesList.size());


//        DataSourceJpa.supplyDatabase(employeesRepository);


//        try {
//
////            hibernateDao = new HibernateDao();
////            DataSource.supplyDatabase();
////            DataSourceJpa.supplyDatabase(carsRepository, employeesRepository);
////            System.out.println(employeesRepository.findOne(1));
//
////            int i = 0;
////            employeesRepository.findAll().forEach();
////            for(Employees employees : employeesList){
////                System.out.println(employees.toString());
////            }
////            for (Employees employees : employeesRepository.findAll()){
//////                System.out.println(employees.toString());
////                employeesList.add(employees);
////                System.out.println(i);
////                i++;
////            }
//            employeesList = employeesRepository.findAll();
//            System.out.println(employeesList);
//
//
//
//        } catch (NullPointerException exception) {
//            System.out.println("No connection with database");
//            exception.getMessage();
//            employeesList = new ArrayList<>();
//            Employees employee1 = new Employees(1, "Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
//            Employees employee2 = new Employees(2, "Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
//            Employees employee3 = new Employees(3, "Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
//            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));
//        }
    }

    @RequestMapping("/")
    public String indexGet() {
        return "/index";
    }

    @RequestMapping("/allEmployees")
    public ModelAndView showEmployeesList(Model model) {
//        saveEmployeeToDatabase();
        employeesList = employeesServiceImpl.getAll();
        return new ModelAndView("/all_employees_list", "list", employeesList);
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("employee", new Employees());
        return "/add_employee_form";
    }

    @RequestMapping(value = "/saveEmployee")
    public ModelAndView save(@ModelAttribute(value = "employee") Employees employee) {
        if (employee.getId() == 0) {
            addEmployeeToDatabase(employee);
//            employee.setId(employeesList.size());
//            employeesList.add(employee);
        } else {
            updateEmployeeInDatabase(employee);
//            employeesList.set(employee.getId() - 1, employee);
        }
        return new ModelAndView("redirect:/allEmployees");
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        deleteEmployeeFromDatabase(employee);
//        employeesList.remove(employee);
        return new ModelAndView("redirect:/allEmployees");
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        return new ModelAndView("/add_employee_form", "employee", employee);
    }


    private Employees getEmployeesById(@RequestParam int id) {
        return employeesList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

//    private void saveEmployeeToDatabase() {
//        employeesRepository.save(new Employees("Piotr", "JPawlak", "Grójecka 28", "Warszawa", 1000, 18, new Date(), 0));
//
//    }



    private void addEmployeeToDatabase(Employees employees) {
        try {
            employeesServiceImpl.create(employees);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployeeInDatabase(Employees employees) {
        try {
            employeesServiceImpl.update(employees);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployeeFromDatabase(Employees employees) {
        try {
            employeesServiceImpl.delete(employees);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
