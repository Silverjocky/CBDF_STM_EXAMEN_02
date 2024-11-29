package com.upiiz.suppliers.controllers;

import com.upiiz.suppliers.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upiiz.suppliers.entities.Supplier;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    //GET de todos
    @GetMapping
    public ResponseEntity<List<Supplier>> getAll(){
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getById(@PathVariable Long id){
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    //POST
    @PostMapping
    public ResponseEntity<Supplier> createsupplier(@RequestBody Supplier supplier){
        return ResponseEntity.ok(supplierService.createSupplier(supplier));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updatesupplier(@RequestBody Supplier supplier, @PathVariable Long id){
        supplier.setSupplierId(id);
        return ResponseEntity.ok(supplierService.updateSupplier(supplier));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Supplier> deletesupplier(@PathVariable Long id){
        supplierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}