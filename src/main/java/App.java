import controller.MainController;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.CustomerService;
import util.HibernateUtil;

import java.util.List;

public class App {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        List<Customer> customers = customerService.findByFullName("Kowal", null);
        System.out.println("Test");
    }
}