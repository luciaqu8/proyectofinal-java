package com.techlab.proyectofinal_java.controller;

import com.techlab.proyectofinal_java.exceptions.*;
import com.techlab.proyectofinal_java.models.pedido.Pedido;
import com.techlab.proyectofinal_java.models.usuario.LineaCarrito;
import com.techlab.proyectofinal_java.models.usuario.UserInfo;
import com.techlab.proyectofinal_java.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // CREAR USUARIO
    @PostMapping("/new")
    public ResponseEntity<String> crearUsuario(@RequestBody UserInfo userInfo) {
        try {
            UserInfo creado = usuarioService.crearUsuario(userInfo);
            return ResponseEntity.ok("Usuario creado: " + creado.getUsuario() + " (ID: " + creado.getId() + ")");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage() != null ? e.getMessage() : "Error desconocido");
        }
    }

    // AGREGAR AL CARRITO
    @PostMapping("/carrito/agregar")
    public ResponseEntity<String> agregarAlCarrito(@RequestParam int userId, @RequestParam int productoId, @RequestParam int cantidad) {
        try {
            usuarioService.agregarAlCarrito(userId, productoId, cantidad);
            return ResponseEntity.ok("Producto agregado al carrito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // VER CARRITO
    @GetMapping("/carrito/ver")
    public ResponseEntity<List<LineaCarrito>> verCarrito(@RequestParam int userId) {
        try {
            List<LineaCarrito> carrito = usuarioService.verCarrito(userId);
            return ResponseEntity.ok(carrito);
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(404).build();
        }
    }

    // VACIAR CARRITO
    @DeleteMapping("/carrito/vaciar")
    public ResponseEntity<String> vaciarCarrito(@RequestParam int userId) {
        try {
            usuarioService.vaciarCarrito(userId);
            return ResponseEntity.ok("Carrito vaciado.");
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // CONFIRMAR CARRITO
    @PostMapping("/carrito/confirmar")
    public ResponseEntity<String> confirmarCarrito(@RequestParam int userId) {
        try {
            Pedido pedido = usuarioService.confirmarCarrito(userId);
            return ResponseEntity.ok("Pedido confirmado. Total: $" + pedido.getTotal());
        } catch (Exception | CarritoVacioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // VER PEDIDOS
    @GetMapping("/pedidos")
    public ResponseEntity<?> obtenerPedidosUsuario(@RequestParam int userId) {
        try {
            UserInfo usuario = usuarioService.buscarUsuarioPorId(userId);
            return ResponseEntity.ok(usuario.getPedidos());
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // MANEJO DE ERRORES
    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<String> manejarUsuarioNoEncontrado(UsuarioNoEncontradoException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}