package com.nguyensao.customer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nguyensao.customer.dto.AddressDTO;
import com.nguyensao.customer.dto.CustomerDTO;
import com.nguyensao.customer.enums.RoleEnum;
import com.nguyensao.customer.exception.AppException;
import com.nguyensao.customer.model.Address;
import com.nguyensao.customer.model.Customer;
import com.nguyensao.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
            RoleService roleService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public CustomerDTO create(CustomerDTO customerDTO) {
        checkEmail(customerDTO.getEmail());
        Customer customer = mapToEntity(customerDTO, new Customer());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setRole(roleService.findByName(customerDTO.getRoleEnum()));
        return mapToDTO(customerRepository.save(customer), new CustomerDTO());
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .toList();
    }

    public Customer getUserByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new AppException("Email không tồn tại"));
    }

    public RoleEnum getUserRoleByEmail(String email) {
        return customerRepository.findRoleByEmail(email);
    }

    // CheckEmail
    public void checkEmail(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new AppException("Email đã tồn tại nhưng chưa được kích hoạt. Vui lòng kích hoạt tài khoản");
        }
    }

    // Kiểm tra email
    public void findEmail(String email) throws AppException {
        if (!customerRepository.existsByEmail(email)) {
            throw new AppException("Email không tồn tại");
        }
    }

    // Mapper
    private CustomerDTO mapToDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setFullname(customer.getFullname());
        customerDTO.setRoleEnum(customer.getRole().getName());
        Set<AddressDTO> addressDTOs = customer.getAddresses().stream().map(address -> {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(address.getId());
            addressDTO.setAddress(address.getAddress());
            return addressDTO;
        }).collect(Collectors.toSet());
        customerDTO.setAddresses(addressDTOs);
        return customerDTO;
    }

    private Customer mapToEntity(CustomerDTO customerDTO, Customer customer) {
        customer.setId(customerDTO.getId());
        customer.setEmail(customerDTO.getEmail());
        customer.setFullname(customerDTO.getFullname());
        Set<Address> addressSet = new HashSet<>();
        if (customerDTO.getAddresses() != null && !customerDTO.getAddresses().isEmpty()) {
            addressSet = customerDTO.getAddresses().stream().map(addressDTO -> {
                Address address = new Address();
                address.setId(addressDTO.getId());
                address.setAddress(addressDTO.getAddress());
                address.setCustomer(customer);
                return address;
            }).collect(Collectors.toSet());
        }
        customer.setAddresses(addressSet);
        return customer;
    }
}
