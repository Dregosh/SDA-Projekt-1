import controller.MainController;
import service.CustomerService;
import service.ProductService;

public class App {
    public static void main(String[] args) {
        new MainController().start();
        //initializeDB();
    }

    private static void initializeDB() {
        new ProductService().addInitialProductsToDB();
        new CustomerService().initialCustomers();
    }
}
