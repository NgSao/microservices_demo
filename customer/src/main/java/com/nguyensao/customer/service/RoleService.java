package com.nguyensao.customer.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nguyensao.customer.dto.RoleDTO;
import com.nguyensao.customer.enums.RoleEnum;
import com.nguyensao.customer.exception.AppException;
import com.nguyensao.customer.model.Role;
import com.nguyensao.customer.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO findById(String id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(() -> new AppException("Invalid role Id:" + id));
    }

    public RoleDTO create(RoleDTO roleDTO) {
        Role role = mapToEntity(roleDTO, new Role());
        return mapToDTO(roleRepository.save(role), new RoleDTO());
    }

    public RoleDTO update(RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleDTO.getId())
                .orElseThrow(() -> new AppException("Invalid role Id:" + roleDTO.getId()));
        role = mapToEntity(roleDTO, role);
        return mapToDTO(roleRepository.save(role), new RoleDTO());
    }

    public void delete(String id) {
        checkId(id);
        roleRepository.deleteById(id);
    }

    public Role findByName(RoleEnum roleEnum) {
        return roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new AppException("Role not found: " + roleEnum));
    }

    // Mapper to RoleDTO
    private RoleDTO mapToDTO(Role role, RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(RoleDTO roleDTO, Role role) {
        role.setId(roleDTO.getId());
        if (roleDTO.getName() != null) {
            role.setName(roleDTO.getName());
        }
        return role;
    }

    private void checkId(String id) {
        roleRepository.findById(id).orElseThrow(() -> new AppException("Invalid role Id:" + id));
    }
}
