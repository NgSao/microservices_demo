package com.nguyensao.customer.dto;

import java.util.Set;

import com.nguyensao.customer.enums.RoleEnum;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO {
    String id;

    String fullname;

    String email;

    String password;
    RoleEnum roleEnum;

    Set<AddressDTO> addresses; // Danh sách địa chỉ

}
