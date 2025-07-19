package com.techlab.proyectofinal_java.repositories;

import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoInfo, Integer> {}
