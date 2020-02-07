package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.services.EmployeesServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private List<Employees> employeesList;
    private EmployeesServiceImpl employeesServiceImpl;

    public EmployeeController(EmployeesServiceImpl employeesServiceImpl) {
        this.employeesServiceImpl = employeesServiceImpl;
        employeesList = employeesServiceImpl.getAll();
    }

    @RequestMapping("/seeAll")
    public ModelAndView showEmployeesList(Model model) {
        employeesList = employeesServiceImpl.getAll();
        return new ModelAndView("all_employees_list", "list", employeesList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("employee", new Employees());
        return "add_employee_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "employee") Employees employee,
                             @RequestParam(value = "date") String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateParsed = LocalDate.parse(date, formatter);
        employee.setStartJobDate(dateParsed);

        if (employee.getId() == 0) {
            addEmployeeToDatabase(employee);
        } else {
            updateEmployeeInDatabase(employee);
        }
        return new ModelAndView("redirect:/employee/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        deleteEmployeeFromDatabase(employee);
        return new ModelAndView("redirect:/employee/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        return new ModelAndView("add_employee_form", "employee", employee);
    }


    private Employees getEmployeesById(@RequestParam int id) {
        return employeesList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

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
