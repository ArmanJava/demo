package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class RegistrationDto implements Serializable {
    private static final long serialVersionUID = 6524236637732252143L;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Lastname cannot be null")
    private String lastname;
    @Size(min = 6, max = 30, message
            = "Password must be between 6 and 30 characters")
    private String password;
    @NotNull(message = "email cannot be null")
    @Email(message = "Email should be valid")
    private String email;
}
