package com.techlab.proyectofinal_java.models.productos;

import com.techlab.proyectofinal_java.exceptions.CantidadInvalidaException;
import com.techlab.proyectofinal_java.exceptions.StockNegativoException;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class ProductoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private String imagen;
    private String categoria;

    public ProductoInfo() {}

    public ProductoInfo(String nombre, String descripcion, int precio, int stock, String imagen, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    // Validar
    public void validar() throws CantidadInvalidaException, StockNegativoException {
        if (this.precio <= 0) {
            throw new CantidadInvalidaException("El precio debe ser mayor a cero.");
        }
        if (this.stock <= 0) {
            throw new StockNegativoException("El stock debe ser mayor a cero.");
        }
        if (this.nombre == null || this.nombre.isBlank() ||
                this.descripcion == null || this.descripcion.isBlank() ||
                this.categoria == null || this.categoria.isBlank() ||
                this.imagen == null || this.imagen.isBlank()) {
            throw new IllegalArgumentException("Todos los campos deben estar completos.");
        }
        if (!this.imagen.matches("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$")) {
            throw new IllegalArgumentException("La imagen debe ser un link válido.");
        }
    }

    // Getters y Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public int getPrecio() {return precio;}
    public void setPrecio(int precio) {this.precio = precio;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}
    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
}