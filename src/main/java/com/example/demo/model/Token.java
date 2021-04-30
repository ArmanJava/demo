package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String token;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    private boolean isActive;
}
