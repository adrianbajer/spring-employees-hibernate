package spring.hibernate;

import spring.repository.CarsRepository;
import spring.repository.EmployeesRepository;

import java.util.Date;

public class DataSourceJpa {


    public static void supplyDatabase(EmployeesRepository employeesRepository) {


//        try {
//            isDatabaseConnection = Boolean.TRUE;
//        } catch (NullPointerException e) {
//            System.out.println("No database connection.");
//            e.getStackTrace();
//        }

        Employees employee1 = new Employees("Piotr", "JPawlak", "Grójecka 28", "Warszawa", 1000, 18, new Date(), 0);
        Employees employee2 = new Employees("Paweł", "Kaczyński", "Zielona 28", "Kraków", 2000, 28, new Date(), 1);
        Employees employee3 = new Employees("Anna", "JPawlak", "Grójecka 28", "Warszawa", 3000, 45, new Date(), 1);
        Employees employee4 = new Employees("Katarzyna", "Gierszałt", "Marymoncka 28", "Gdynia", 4000, 39, new Date(), 1);
        Employees employee5 = new Employees("Maciej", "Józefowicz", "Koszykowa 28", "Warszawa", 5000, 31, new Date(), 1);
        Employees employee6 = new Employees("Genowefa", "Pigwa", "Rybickiego 128", "Zamość", 6000, 29, new Date(), 1);
        Employees employee7 = new Employees("Piotr", "Złomczyński", "Szucha 8", "Warszawa", 1500, 18, new Date(), 0);
//
////        // sprawdzamy, czy dane są już w bazie czy nie
////        List<Employees> verificationList = hibernateDao.get(Employees.class);
////
////        List<Employees> listToAdd = new ArrayList<>(Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6, employee7));
////        listToAdd.removeAll(verificationList);
////
////        for (Employees employee : listToAdd) {
////            hibernateDao.saveEntity(employee);
////        }
//
//        Cars car1 = new Cars(employee6, "Subaru", "Forester", new Date());
//        Cars car2 = new Cars(employee2, "Ford", "Fiesta", new Date());
//        Cars car3 = new Cars(employee3, "Fiat", "126jpa", new Date());
//        Cars car4 = new Cars(employee4, "Subaru", "Forester", new Date());
//        Cars car5 = new Cars(employee5, "Subaru", "Forester", new Date());
//
////        employee1 = employeesRepository.save(employee1);
////        employee2 = employeesRepository.save(employee2);
////        employee3 = employeesRepository.save(employee3);
////        employee4 = employeesRepository.save(employee4);
////        employee5 = employeesRepository.save(employee5);
////        employee6 = employeesRepository.save(employee6);
////        employee7 = employeesRepository.save(employee7);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        employeesRepository.save(employee4);
        employeesRepository.save(employee5);
        employeesRepository.save(employee6);
        employeesRepository.save(employee7);
//
//        carsRepository.save(car1);
//        carsRepository.save(car2);
//        carsRepository.save(car3);
//        carsRepository.save(car4);
//        carsRepository.save(car5);
//
////        System.out.println(employeesRepository.findOne(1));
//
    }
}
