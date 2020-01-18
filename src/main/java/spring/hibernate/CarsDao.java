package spring.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CarsDao {

    public void saveCar(Cars car) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Cars> getCars() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Cars", Cars.class).list();
        }
    }

    public void updateCars(Cars car) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


}
