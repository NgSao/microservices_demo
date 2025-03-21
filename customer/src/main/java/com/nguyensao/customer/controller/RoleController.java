package com.nguyensao.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyensao.customer.dto.RoleDTO;
import com.nguyensao.customer.service.RoleService;

@RestController
@RequestMapping("/api2/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.create(roleDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO roleDTO) {
        return ResponseEntity.ok(roleService.update(roleDTO));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        roleService.delete(id);
        return "Xóa thành công";
    }

}
