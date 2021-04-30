package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.model.Customer;
import com.example.demo.model.Item;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ItemRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Customer> findByLastName(String lastname) {
        return customerRepository.findByLastname(lastname);
    }

    public void save(Customer newCustomer) {
        customerRepository.save(newCustomer);
    }

    public List<ItemDto> getShoppingCart(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Customer not found"));
        return customer.getItems().stream().map(ItemDto::createInstance).collect(Collectors.toList());
    }

    public void addSingleItemToShoppingCart(Long customerId, Long itemId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Customer not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ObjectNotFoundException(Item.class, "Item not found"));
        customer.getItems().add(item);
        customerRepository.save(customer);
    }

    public void addItemsToShoppingCart(Long customerId, Set<Long> itemIds) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Customer not found"));
        Set<Item> items = StreamSupport.stream(itemRepository.findAllById(itemIds).spliterator(), false).collect(Collectors.toSet());
        customer.getItems().addAll(items);
        customerRepository.save(customer);
    }
}
