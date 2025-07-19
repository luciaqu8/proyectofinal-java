package com.techlab.proyectofinal_java.models.usuario;

import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(
            name = "carrito_lineas",
            joinColumns = @JoinColumn(name = "carrito_id")
    )
    private List<LineaCarrito> items = new ArrayList<>();

    public Carrito() {}

    public int getId() {
        return id;
    }

    public List<LineaCarrito> getItems() {
        return items;
    }

    public void setItems(List<LineaCarrito> items) {
        this.items = items;
    }

    public void agregarProducto(ProductoInfo producto, int cantidad) {
        for (LineaCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        LineaCarrito nuevaLinea = new LineaCarrito(producto, cantidad);
        items.add(nuevaLinea);
    }

    public void vaciarCarrito() {
        items.clear();
    }
}
