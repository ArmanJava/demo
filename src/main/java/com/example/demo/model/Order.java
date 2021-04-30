package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String total;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //    @OneToMany(mappedBy = "order")
    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<Item> items = new HashSet<>();
}
