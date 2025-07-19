package com.techlab.proyectofinal_java.service;

import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import com.techlab.proyectofinal_java.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {


    @Autowired
    private ProductoRepository productoRepository;


    public List<ProductoInfo> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public ProductoInfo agregarProducto(ProductoInfo producto) {
        return productoRepository.save(producto);
    }

    public ProductoInfo buscarPorId(int id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminarProducto(int id) {
        productoRepository.deleteById(id);
    }

    public ProductoInfo actualizarProducto(int id, ProductoInfo productoActualizado) {
        ProductoInfo producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setImagen(productoActualizado.getImagen());
        producto.setCategoria(productoActualizado.getCategoria());

        return productoRepository.save(producto);
    }
}
