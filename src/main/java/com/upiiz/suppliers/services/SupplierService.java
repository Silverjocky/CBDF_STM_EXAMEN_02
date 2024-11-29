package com.upiiz.suppliers.services;

import com.upiiz.suppliers.respositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upiiz.suppliers.entities.Supplier;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id){
        return supplierRepository.findById(id).orElse(null);
    }

    //POST
    public Supplier createSupplier(Supplier supplier){
        return supplierRepository.save(supplier);
    }

    //PUT
    public Supplier updateSupplier(Supplier supplier){
        if(supplierRepository.existsById(supplier.getSupplierId())){
            return supplierRepository.save(supplier);
        }
        return null;
    }

    //DELETE
    public void deleteById(Long id){
        if(supplierRepository.existsById(id)){
            supplierRepository.deleteById(id);
        }
    }
}