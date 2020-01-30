package spring.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataSource {

    public static boolean isDatabaseConnection = Boolean.FALSE;

    public static void supplyDatabase() {
        HibernateDao hibernateDao = null;

        try {
            hibernateDao = new HibernateDao();
            isDatabaseConnection = Boolean.TRUE;
        } catch (NullPointerException e) {
            System.out.println("No database connection.");
            e.getStackTrace();
        }

        Employees employee1 = new Employees("Piotr", "Pawlak", "Grójecka 28", "Warszawa", 1000, 18, new Date(), 0);
        Employees employee2 = new Employees("Paweł", "Kaczyński", "Zielona 28", "Kraków", 2000, 28, new Date(), 1);
        Employees employee3 = new Employees("Anna", "Pawlak", "Grójecka 28", "Warszawa", 3000, 45, new Date(), 1);
        Employees employee4 = new Employees("Katarzyna", "Gierszałt", "Marymoncka 28", "Gdynia", 4000, 39, new Date(), 1);
        Employees employee5 = new Employees("Maciej", "Józefowicz", "Koszykowa 28", "Warszawa", 5000, 31, new Date(), 1);
        Employees employee6 = new Employees("Genowefa", "Pigwa", "Rybickiego 128", "Zamość", 6000, 29, new Date(), 1);
        Employees employee7 = new Employees("Piotr", "Złomczyński", "Szucha 8", "Warszawa", 1500, 18, new Date(), 0);

        // sprawdzamy, czy dane są już w bazie czy nie
        List<Employees> verificationList = hibernateDao.get(Employees.class);

        List<Employees> listToAdd = new ArrayList<>(Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6, employee7));
        listToAdd.removeAll(verificationList);

        for (Employees employee : listToAdd) {
            hibernateDao.saveEntity(employee);
        }

        Cars car1 = new Cars(employee6, "Subaru", "Forester", new Date());
        Cars car2 = new Cars(employee2, "Ford", "Fiesta", new Date());
        Cars car3 = new Cars(employee3, "Fiat", "126p", new Date());
        Cars car4 = new Cars(employee4, "Subaru", "Forester", new Date());
        Cars car5 = new Cars(employee5, "Subaru", "Forester", new Date());

        hibernateDao.saveEntity(car1);
        hibernateDao.saveEntity(car2);
        hibernateDao.saveEntity(car3);
        hibernateDao.saveEntity(car4);
        hibernateDao.saveEntity(car5);

        List<Employees> listForPrinter1 = listToAdd;
        List<Employees> listForPrinter2 = new ArrayList<>(Arrays.asList(employee1, employee6, employee7));

        Printers printer1 = new Printers(listForPrinter1, "Hewlett Packard", "1234h", true, true);
        Printers printer2 = new Printers("EasyJet", "asd", true, false);
        Printers printer3 = new Printers(listForPrinter2, "Optimus", "Prime", false, false);

        hibernateDao.saveEntity(printer1);
        hibernateDao.saveEntity(printer2);
        hibernateDao.saveEntity(printer3);

    }


}
