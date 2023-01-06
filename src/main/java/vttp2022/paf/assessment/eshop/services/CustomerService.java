package vttp2022.paf.assessment.eshop.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Customer> findCustomerByName(String name){
        return customerRepository.findCustomerByName(name);
    }

    public String saveOrder(Customer c, Order o){
        return orderRepository.saveOrder(c, o);
    }

    // TASK 6
    public String getTotalDispatchPendingOrdersByCustomer(String name){
        return customerRepository.getTotalDispatchedByCustomer(name);
    }
}
