package com.example.demo.service;

import com.example.demo.dto.OrderRequestDto;
import com.example.demo.model.Customer;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItemsToOrder(OrderRequestDto orderRequestDto) {

        Set<Item> items = StreamSupport.stream(itemRepository.findAllById(orderRequestDto.getItemIds()).spliterator(), false).collect(Collectors.toSet());
        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId()).orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Customer not found"));
        Order order = new Order();
        order.setItems(items);
        order.setCustomer(customer);
        orderRepository.save(order);
    }
}
