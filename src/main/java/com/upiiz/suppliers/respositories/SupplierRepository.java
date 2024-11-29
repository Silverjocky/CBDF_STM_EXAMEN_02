package com.upiiz.suppliers.respositories;

import com.upiiz.suppliers.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
}