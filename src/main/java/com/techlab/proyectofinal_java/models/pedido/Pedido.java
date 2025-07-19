//package com.techlab.proyectofinal_java.models.pedido;
//
//import com.techlab.proyectofinal_java.models.usuario.LineaCarrito;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//@Table(name = "usuarios")
//public class Pedido {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private List<LineaCarrito> items;
//    private int total;
//
//    public Pedido(int id, List<LineaCarrito> items, int total) {
//        this.id = id;
//        this.items = items;
//        this.total = total;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public List<LineaCarrito> getItems() {
//        return items;
//    }
//
//    public int getTotal() {
//        return total;
//    }
//}
package com.techlab.proyectofinal_java.models.pedido;

import com.techlab.proyectofinal_java.models.usuario.LineaCarrito;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "lineas_pedido", joinColumns = @JoinColumn(name = "pedido_id"))
    private List<LineaCarrito> items = new ArrayList<>();

    private int total;

    public Pedido() {}

    public Pedido(List<LineaCarrito> items, int total) {
        this.items = items;
        this.total = total;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public List<LineaCarrito> getItems() {
        return items;
    }

    public void setItems(List<LineaCarrito> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
