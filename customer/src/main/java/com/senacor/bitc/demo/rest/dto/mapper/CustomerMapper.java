package com.senacor.bitc.demo.rest.dto.mapper;

import com.senacor.bitc.demo.domain.Customer;
import com.senacor.bitc.demo.rest.CustomerAddressController;
import com.senacor.bitc.demo.rest.CustomerController;
import com.senacor.bitc.demo.rest.dto.LinkRelations;
import com.senacor.bitc.demo.rest.dto.request.CustomerRequest;
import com.senacor.bitc.demo.rest.dto.response.CustomerResponse;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CustomerMapper {

    public Customer fromCustomerRequestToCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .birthDate(customerRequest.getBirthDate())
                .comment(customerRequest.getComment())
                .build();
    }

    public CustomerResponse fromCustomerToCustomerResponse(Customer customer) {
        return addCustomerResponseLinks(
                CustomerResponse.builder()
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .birthDate(customer.getBirthDate())
                        .key(customer.getId())
                        .comment(customer.getComment())
                        .build(),
                customer);
    }

    public static CustomerResponse addCustomerResponseLinks(CustomerResponse customerResponse, Customer customer) {
        customerResponse.add(
                linkTo(methodOn(CustomerController.class)
                        .getCustomerById(customerResponse.getKey()))
                        .withSelfRel());

        if (customer.getCustomerAddress() != null) {
            customerResponse.add(linkTo(methodOn(CustomerAddressController.class)
                    .getCustomerAddress(customerResponse.getKey()))
                    .withRel(LinkRelations.ADDRESS.getName()));
        }

        return customerResponse;
    }

}