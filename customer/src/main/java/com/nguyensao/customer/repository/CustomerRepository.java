package com.nguyensao.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nguyensao.customer.enums.RoleEnum;
import com.nguyensao.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

    @Query("SELECT u.role.name FROM Customer u WHERE u.email = :email")
    RoleEnum findRoleByEmail(@Param("email") String email);
}
