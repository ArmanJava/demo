package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = {"ROLE_CUSTOMER", "ROLE_ADMIN"})
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    List<Customer> mockCustomer = Collections.singletonList(new Customer("TestName", "TestLastName"));

    @Test
    public void retrieveDetailsForCourse() throws Exception {

        Mockito.when(
                customerService.findByLastName(Mockito.anyString())).thenReturn(mockCustomer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/find?lastname=TestLastName").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"id\":null,\"name\":\"TestName\",\"lastname\":\"TestLastName\",\"orders\":[],\"items\":[]}]";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}
