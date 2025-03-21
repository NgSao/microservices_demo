package com.nguyensao.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyensao.customer.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

}
