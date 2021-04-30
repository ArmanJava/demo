package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @OneToMany(mappedBy="customer")
    private Set<Order> orders = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "link_customer_item",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
            Set<Item> items = new HashSet<>();

    public Customer() {}

    public Customer(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }


    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastname='%s']",
                id, name, lastname);
    }


}
