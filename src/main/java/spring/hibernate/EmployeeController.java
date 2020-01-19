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
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    @RequestMapping(value = "/empform", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("employee", new Employees());
        return "emp/add_employee_form";
    }

    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "employee") Employees employee) {
        if (employee.getId() == 0) {
            employeeDao.saveEmployee(employee);
        } else {
            employeeDao.updateEmployees(employee);
        }
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value = "/delete_emp", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        employeeDao.deleteEmployees(employee);
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value = "/edit_emp", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "employee_id") String employee_id) {
        Employees employee = getEmployeesById(Integer.parseInt(employee_id));
        return new ModelAndView("emp/add_employee_form", "employee", employee);
    }

    @RequestMapping("/viewemp")
    public ModelAndView viewemp(Model model) {
        List<Employees> list = employeeDao.getEmployees();
        return new ModelAndView("emp/all_employees_list", "list", list);
    }

    private Employees getEmployeesById(@RequestParam int id) {
        List<Employees> list = employeeDao.getEmployees();
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }

}
