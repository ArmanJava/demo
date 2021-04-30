package com.example.demo.service;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.Roles;
import com.example.demo.model.Customer;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtAuthenticationResponseDto;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private CustomerRepository customerRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setTokenProvider(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(RegistrationDto registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setLastname(registrationDTO.getLastname());
        user.setName(registrationDTO.getName());
        Role role = roleRepository.findByName(Roles.ROLE_CUSTOMER).orElseThrow(() -> new ObjectNotFoundException(Role.class, "Role not found"));
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(user);
        Customer customer = new Customer();
        customer.setName(user.getName());
        customer.setLastname(user.getLastname());
        customerRepository.save(customer);
    }

    public JwtAuthenticationResponseDto signIn(UserDto userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        log.info("User with [email: {}] has logged in", userPrincipal.getEmail());
        return new JwtAuthenticationResponseDto(jwt);
    }
}
