package spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.model.Employees;
import spring.model.Printers;
import spring.services.EmployeesServiceImpl;
import spring.services.PrintersServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("printer")
public class PrinterController {
    private List<Printers> printersList;
    private List<Employees> employeesList;
    private PrintersServiceImpl printersServiceImpl;
    private EmployeesServiceImpl employeesServiceImpl;

    public PrinterController(PrintersServiceImpl printersServiceImpl, EmployeesServiceImpl employeesServiceImpl) {

        this.printersServiceImpl = printersServiceImpl;
        this.employeesServiceImpl = employeesServiceImpl;

        printersList = printersServiceImpl.getAll();
        employeesList = employeesServiceImpl.getAll();

    }

    @RequestMapping("/seeAll")
    public ModelAndView showPrinterList(Model model) {
        printersList = printersServiceImpl.getAll();
        return new ModelAndView("all_printers_list", "printersList", printersList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        employeesList = employeesServiceImpl.getAll();
        model.addAttribute("printer", new Printers());
        model.addAttribute("employeesList", employeesList);
        model.addAttribute("chosenEmployeesIdsList", new ArrayList<>());
        return "add_printer_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "printer") Printers printer
            , @ModelAttribute(value = "chosenEmployeesIdsList") ArrayList<String> chosenEmployeesIdsList) {

        employeesList = employeesServiceImpl.getAll();

        List<Integer> idsToIntList = new ArrayList<>();
        for (String idToConvert : chosenEmployeesIdsList) {
            Integer id = Integer.parseInt(idToConvert);
            idsToIntList.add(id);
        }

        Set<Employees> chosenEmployees = new HashSet<>();
        for (Employees employees : employeesList) {
            if (idsToIntList.contains(employees.getId())) {
                chosenEmployees.add(employees);
            }
        }
        printer.setEmployeesSet(chosenEmployees);

        if (printer.getId() == 0) {
            addPrinterToDatabase(printer);
        } else {
            updatePrinterInDatabase(printer);
        }
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "printer_id") String printerId) {
        Printers printer = getPrinterById(Integer.parseInt(printerId));
        deletePrinterFromDatabase(printer);
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "printer_id") String printerId) {
        Printers printer = getPrinterById(Integer.parseInt(printerId));
        return new ModelAndView("add_printer_form", "printer", printer);
    }

    private Printers getPrinterById(@RequestParam int id) {
        return printersList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void addPrinterToDatabase(Printers printer) {
        try {
            printersServiceImpl.create(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updatePrinterInDatabase(Printers printer) {
        try {
            printersServiceImpl.create(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deletePrinterFromDatabase(Printers printer) {
        try {
            printersServiceImpl.delete(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}
