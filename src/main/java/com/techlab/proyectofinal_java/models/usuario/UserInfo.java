package com.techlab.proyectofinal_java.models.usuario;

import com.techlab.proyectofinal_java.models.pedido.Pedido;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String usuario;
    private String clave;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrito_id", referencedColumnName = "id")
    private Carrito carrito = new Carrito();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Pedido> pedidos = new ArrayList<>();

    public UserInfo() {}

    public UserInfo(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }
}