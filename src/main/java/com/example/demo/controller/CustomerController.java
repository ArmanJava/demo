package com.example.demo.controller;

import com.example.demo.dto.ItemDto;
import com.example.demo.dto.OrderRequestDto;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import com.example.demo.service.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CustomerController {
    private CustomerService customerService;
    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Create custimer
     * @param newCustomer
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public Customer createCustomer(@RequestBody Customer newCustomer) {
        customerService.save(newCustomer);
        return newCustomer;
    }

    /**
     * Find customer by last name
     * @param lastname
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/find")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public List<Customer> findByLastName(@RequestParam String lastname) {
        return customerService.findByLastName(lastname);
    }

    /**
     * get customer's shopping cart
     * @param id
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/cart/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public List<ItemDto> getShoppingCart(@PathVariable Long id) {
        return customerService.getShoppingCart(id);
    }

    /**
     * add a single item to cart
     *
     * @param customerId
     * @param itemId
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/addItem/{customerId}/{itemId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> addSingleItemToShoppingCart(@PathVariable Long customerId, @PathVariable Long itemId) {
        customerService.addSingleItemToShoppingCart(customerId, itemId);
        return ResponseEntity.ok().build();
    }

    /**
     * add multiple items to cart
     * @param customerId
     * @param itemIds
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("/addItems/{customerId}/{itemIds}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> addItemsToShoppingCart(@PathVariable Long customerId, @PathVariable Set<Long> itemIds) {
        customerService.addItemsToShoppingCart(customerId, itemIds);
        return ResponseEntity.ok().build();
    }

    /**
     * place order
     * @param orderRequestDto
     * @return
     */
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/addItemsToOrder")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> addItemsToOrder(@RequestBody OrderRequestDto orderRequestDto) {
        itemService.addItemsToOrder(orderRequestDto);
        return ResponseEntity.ok().build();
    }
}
