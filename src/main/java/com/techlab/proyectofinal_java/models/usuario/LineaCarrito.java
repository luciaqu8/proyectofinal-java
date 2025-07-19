package com.techlab.proyectofinal_java.models.usuario;

import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import jakarta.persistence.*;

@Embeddable
public class LineaCarrito {

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoInfo producto;

    private int cantidad;

    public LineaCarrito() {}

    public LineaCarrito(ProductoInfo producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoInfo getProducto() {
        return producto;
    }

    public void setProducto(ProductoInfo producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
