package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("printer")
public class PrinterController {
    private List<Printers> printersList;
    private List<Employees> employeesList;
    private HibernateDao hibernateDao;

    //Grupa 1. Ma za zadanie klasę dodać klasę, która będzie obsługiwała przypisane do pracownika drukarki z adnotacją
    // @ManyToMany oraz dorobić do niej odpowiedni formularz.
    //Dodaje przycisk który umożliwi przywrócenie bazy danych do punktu początkowego oraz wystawia aplikacje na heroku.
    // Grupa 2. Przepina projekt na jparepository/crudrepository oraz tworzy zdalną bazę danych, którą podpina do projektu.
    // Czas do 9.02. Sposób oddania to wysłanie linku do wspólnego repozytorium oraz linku do działającej aplikacji

    public PrinterController() {
        try {
            hibernateDao = new HibernateDao();
            DataSource.supplyDatabase();
            employeesList = hibernateDao.get(Employees.class);
            printersList = hibernateDao.get(Printers.class);
        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();

            employeesList = new ArrayList<>();
            Employees employee1 = new Employees(1, "Adam", "Kowalski", "Piękna 3/13", "Warszawa", 1000, 18, new Date(), 1);
            Employees employee2 = new Employees(2, "Rafał", "Nowak", "gen. Maczka 3/13", "Kraków", 2000, 23, new Date(), 0);
            Employees employee3 = new Employees(3, "Tomek", "Barbara", "gen. Maczka 3/13", "Kielce", 3000, 27, new Date(), 1);
            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));

            printersList = new ArrayList<>();
            Set<Employees> setForPrinter1 = new HashSet<>(Collections.singletonList(employee1));
            Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employee1, employee2, employee3));

            Printers printer1 = new Printers(setForPrinter1, 1, "Hewlett Packard", "1234h", true, true);
            Printers printer2 = new Printers(2, "EasyJet", "asd", true, false);
            Printers printer3 = new Printers(setForPrinter2, 3, "Optimus", "Prime", false, false);
            printersList.addAll(Arrays.asList(printer1, printer2, printer3));
        }
    }

    @RequestMapping("/seeAll")
    public ModelAndView showPrinterList(Model model) {
        return new ModelAndView("/all_printers_list", "printersList", printersList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("printer", new Printers());
        model.addAttribute("employeesList", employeesList);
        model.addAttribute("chosenEmployeesIdsList", new ArrayList<>());
        return "/add_printer_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "printer") Printers printer
            , @ModelAttribute(value = "chosenEmployeesIdsList") ArrayList<String> chosenEmployeesIdsList) {

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
            printer.setId(printersList.size());
            printersList.add(printer);
        } else {
            updatePrinterInDatabase(printer);
            //printersList.set(printer.getId() - 1, printer);
        }
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "printer_id") String printerId) {
        Printers printer = getPrinterById(Integer.parseInt(printerId));
        deletePrinterFromDatabase(printer);
        printersList.remove(printer);
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "printer_id") String printerId) {
        Printers printer = getPrinterById(Integer.parseInt(printerId));
        return new ModelAndView("/add_printer_form", "printer", printer);
    }

    private Printers getPrinterById(@RequestParam int id) {
        return printersList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void addPrinterToDatabase(Printers printer) {
        try {
            hibernateDao.saveEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updatePrinterInDatabase(Printers printer) {
        try {
            hibernateDao.updateEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deletePrinterFromDatabase(Printers printer) {
        try {
            hibernateDao.deleteEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}
