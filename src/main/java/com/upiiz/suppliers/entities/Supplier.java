package com.upiiz.suppliers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data // Para los gets y sets (no se ven pero si funcionan)
@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String contact;
    private String name;
    private String phone;
}