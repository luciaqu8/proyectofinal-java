package com.techlab.proyectofinal_java.dto;

import com.techlab.proyectofinal_java.models.productos.ProductoInfo;

public class ProductoResponseDTO {

    private int id;
    private String nombre;
    private String descripcion;
    private int precio;
    private String categoria;
    private String imagen;
    private int stock;

    public ProductoResponseDTO(ProductoInfo producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.categoria = producto.getCategoria();
        this.imagen = producto.getImagen();
        this.stock = producto.getStock();
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public int getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public String getImagen() { return imagen; }
    public int getStock() { return stock; }
}