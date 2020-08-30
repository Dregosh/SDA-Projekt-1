import controller.MainController;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.CustomerService;
import util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        Customer customer = customerService.findById(1L);
        System.out.println(customer);
    }
}