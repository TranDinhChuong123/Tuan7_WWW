package vn.edu.iuh.fit.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.backend.enums.EmployeeStatus;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private  OrderRepository orderRepository;


    @GetMapping("/shop-home")
    public String index(Model model){
        List<Product> products = productRepository.findAll();

        model.addAttribute("products",products);
        return "index";
    }
    @GetMapping("/shop-cart-add/{id}")
    public String buyId(HttpSession session, @PathVariable("id") long id, RedirectAttributes redirectAttributes){
        Optional<Product> product = productRepository.findById(id);

        List <Product> products = null;
//        session.setAttribute("quantity",quantity);

        List<Product> cartProducts= (List<Product>) session.getAttribute("cartProducts");
        if(cartProducts==null)
            products = new ArrayList<>();
        else{
            products = cartProducts;

        }
        products.add(product.get());
        System.out.println(product.get().getProductImageList().get(0).toString());

        session.setAttribute("cartProducts",products);


        redirectAttributes.addAttribute("id",id);
        return "redirect:/shop-item/{id}";

    }
    @GetMapping("/shop-cart")
    public String shopCart(HttpSession session,Model model) {
        List<Product> products =(List<Product>) session.getAttribute("cartProducts");
        model.addAttribute("products",products);

        return "shop-cart"; // Hoặc trả về trang kết quả khác tùy thuộc vào yêu cầu của bạn
    }
//    @GetMapping("/shop-cart/delete/{id}")
//    public String deleteItemShopCrart(HttpSession session,@PathVariable Long id) {
//        List<Product> products =(List<Product>) session.getAttribute("cartProducts");
//        for (Product product: products) {
//            if(product.getProduct_id()==id){
//                products.remove(product);
//                break;
//            }
//        }
//        session.setAttribute("cartProducts",products);
//        return "redirect:/shop-cart";
//    }
@GetMapping("/shop-cart/delete/{id}")
public String deleteItemShopCart(HttpSession session, @PathVariable Long id) {
    List<Product> products = (List<Product>) session.getAttribute("cartProducts");
    Iterator<Product> iterator = products.iterator();
    while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getProduct_id()==(id)) {
            iterator.remove();
            break;
        }
    }

    return "redirect:/shop-cart";
}
    @GetMapping("shop-item/{id}")
    public String shopItemById(Model model,@PathVariable("id") long id){
        Optional<Product> product = productRepository.findById(id);
        model.addAttribute("product",product.get());
        return "shop-item";
    }

    @GetMapping("/check-out")
    public String checkout(HttpSession session){
        session.removeAttribute("cartProducts");
        return "shop-cart";
    }


    @GetMapping("/employees/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }



    @GetMapping("/employees/new")
    public String createStudentForm(Model model) {

        // create student object to hold student form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "create-employee";

    }
    @GetMapping("/employees")
    public String employees(Model model){
        List<Employee> employees = employeeRepository.findAll();

        model.addAttribute("employees",employees);
        return "employee";
    }

    @PostMapping("/employees")
    public String saveStudent(@ModelAttribute("employee") Employee employee) {
        System.out.println(employee.toString());
        employee.setStatus(EmployeeStatus.ACTIVE);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employees/edit/{id}")
    public String editEmployeeForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeRepository.findById(id).orElse(null));
        return "edit-employee";
    }

    @PostMapping("/employees/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("employee") Employee employee,
                                Model model) {
        // get student from database by id
        Employee existingEmployee  = employeeRepository.findById(id).orElse(null);
        existingEmployee.setFullname(employee.getFullname());
        existingEmployee.setDob(employee.getDob());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setAddress(employee.getAddress());

        employeeRepository.save(existingEmployee);
        return "redirect:/employees";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders",orders);
        return "order";
    }

//    @GetMapping("/orders/order-date")
//    public String thongKe(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
//
//                          Model model) {
//        if(toDate==null){
//
//            LocalDateTime startDateTime = fromDate.atStartOfDay();
//            LocalDateTime endDateTime = fromDate.atTime(LocalTime.MAX);
//
//            List<Order> orders = orderRepository.findByOrderDateBetween(startDateTime, endDateTime);
//            model.addAttribute("orders", orders);
//            model.addAttribute("startDate", fromDate);
//        }else{
//
//
//        return "order";
//    }
//    @GetMapping("/orders/order-interval")
//    public String order_interval(@RequestParam(value = "startDate",required = true)  LocalDate fromDate,
//                                 @RequestParam(value = "toDate",required = true)  LocalDate toDate,
//
//                          Model model) {
//
//        LocalDateTime startDateTime = fromDate.atStartOfDay();
//        LocalDateTime endDateTime = toDate.atTime(LocalTime.MAX);
//
//        List<Order> orders = orderRepository.findByOrderDateBetween(startDateTime, endDateTime);
//        model.addAttribute("orders", orders);
//        model.addAttribute("startDate", fromDate);
//        model.addAttribute("endDateTime", endDateTime);
//
//
//        return "order";
//    }
@GetMapping("/orders/order-interval")
public String order_interval(@RequestParam(name = "startDate",required = false ) LocalDate fromDate,
                             @RequestParam(name = "toDate",required = false ) LocalDate toDate,
                             Model model) {
    if (fromDate != null) {
        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime;

        if (toDate != null) {
            endDateTime = toDate.atTime(LocalTime.MAX);

        } else {
            endDateTime = fromDate.atTime(LocalTime.MAX);

        }

        List<Order> orders = orderRepository.findByOrderDateBetween(startDateTime, endDateTime);
        model.addAttribute("orders", orders);
        model.addAttribute("startDate", fromDate);
        model.addAttribute("endDateTime", toDate);
    }

    return "order";
}

    @GetMapping("/orders/search-employeeID")
    public String thongKe(@RequestParam("inputSearch")String s,

                          Model model) {
        List<Order> orders = orderRepository.findOrdersByEmployee_Fullname(s);
        model.addAttribute("orders", orders);

        return "order";
    }










}
