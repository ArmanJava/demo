package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 7919051687026729165L;
    @NotNull(message = "email cannot be null")
    @Email(message = "Email should be valid")
    String email;
    @NotNull(message = "password cannot be null")
    String password;

}
