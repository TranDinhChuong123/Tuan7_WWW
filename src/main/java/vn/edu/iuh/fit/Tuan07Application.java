package vn.edu.iuh.fit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.CustomerRepository;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class Tuan07Application {

    public static void main(String[] args) {
        SpringApplication.run(Tuan07Application.class, args);
    }
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

//    @Bean
//    CommandLineRunner initData(){
//        return args -> {
//            Random rnd =new Random();
//            for (int i = 1; i < 100; i++) {
////                String fullname, LocalDate dob, String email, String phone, String address, EmployeeStatus status
//                String email ="tranDinhChuong"+rnd.nextInt(1,9)+i+"@gmail.com";
//                Employee employee=  new Employee("Tran Dinh Chuong",LocalDate.now(),email,"0918493320","Quang Trung",EmployeeStatus.ACTIVE);
//                employeeRepository.save(employee);
//
//
////                String name, String email, String phone, String address
//                Customer customer = new Customer("Tran Binh AN",email,"039909413","Quang Trung");
//                customerRepository.save(customer);
//
//                Order order = new Order(LocalDateTime.of(2023,11,rnd.nextInt(1,29),rnd.nextInt(1,20),9,22),employee,customer);
//                orderRepository.save(order);
////                String name, String description, String unit, String manufacturer, ProductStatus status
////                Product product = new Product("Mac book","Mac book","12", "Apple",ProductStatus.ACTIVE);
//
//
//
//
//
//            }
//        };
//    }
}
