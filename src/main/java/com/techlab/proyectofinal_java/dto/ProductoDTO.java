package com.techlab.proyectofinal_java.dto;

import com.techlab.proyectofinal_java.exceptions.CantidadInvalidaException;
import com.techlab.proyectofinal_java.exceptions.StockNegativoException;
import com.techlab.proyectofinal_java.models.productos.ProductoInfo;

public class ProductoDTO {

    private String nombre;
    private String descripcion;
    private int precio;
    private String categoria;
    private String imagen;
    private int stock;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public ProductoInfo toProductoInfo() throws CantidadInvalidaException, StockNegativoException {
        if (this.precio <= 0) {
            throw new CantidadInvalidaException("El precio debe ser un número mayor a cero.");
        }

        if (this.stock <= 0) {
            throw new StockNegativoException("El stock debe ser un número mayor a cero.");
        }

        ProductoInfo producto = new ProductoInfo(nombre, descripcion, precio, stock, imagen, categoria);
        producto.setNombre(this.nombre);
        producto.setDescripcion(this.descripcion);
        producto.setCategoria(this.categoria);
        producto.setImagen(this.imagen);
        producto.setPrecio(this.precio);
        producto.setStock(this.stock);
        return producto;
    }
}
