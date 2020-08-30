import controller.MainController;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        Transaction transaction;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainController().start();
    }
}