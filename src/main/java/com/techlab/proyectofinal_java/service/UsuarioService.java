package com.techlab.proyectofinal_java.service;

import com.techlab.proyectofinal_java.exceptions.*;
import com.techlab.proyectofinal_java.models.pedido.Pedido;
import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import com.techlab.proyectofinal_java.models.usuario.Carrito;
import com.techlab.proyectofinal_java.models.usuario.LineaCarrito;
import com.techlab.proyectofinal_java.models.usuario.UserInfo;
import com.techlab.proyectofinal_java.repositories.ProductoRepository;
import com.techlab.proyectofinal_java.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    // Crear Usuario
    public UserInfo crearUsuario(UserInfo userInfo) throws CampoObligatorioException, UsuarioInvalidoException {
        String usuario = userInfo.getUsuario();
        String clave = userInfo.getClave();

        if (usuario == null || usuario.trim().isEmpty())
            throw new CampoObligatorioException("El nombre de usuario es obligatorio.");

        if (!usuario.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new UsuarioInvalidoException("El nombre de usuario debe ser un correo electrónico válido.");

        if (clave == null || clave.trim().isEmpty())
            throw new CampoObligatorioException("La contraseña es obligatoria.");

        if (clave.trim().length() < 8)
            throw new UsuarioInvalidoException("La contraseña debe tener al menos 8 caracteres.");

        if (clave.matches(".*[<>?/$].*"))
            throw new UsuarioInvalidoException("La contraseña contiene caracteres no permitidos: < > ? / $");

        return usuarioRepository.save(userInfo);
    }

    public UserInfo buscarUsuarioPorId(int id) throws UsuarioNoEncontradoException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado."));
    }

    public ProductoInfo buscarProductoPorId(int id) throws ProductoNoEncontradoException {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado."));
    }

    // Agregar al carrito
    public void agregarAlCarrito(int userId, int productoId, int cantidad) throws Exception {
        if (cantidad <= 0)
            throw new CantidadInvalidaException("Cantidad debe ser mayor a 0.");

        UserInfo usuario = buscarUsuarioPorId(userId);
        ProductoInfo producto = buscarProductoPorId(productoId);

        if (producto.getStock() < cantidad)
            throw new StockInsuficienteException("Stock insuficiente para " + producto.getNombre());

        if (usuario.getCarrito() == null) {
            usuario.setCarrito(new Carrito());
        }
        Carrito carrito = usuario.getCarrito();
        carrito.agregarProducto(producto, cantidad);

        usuarioRepository.save(usuario);
    }

    // Ver carrito
    public List<LineaCarrito> verCarrito(int userId) throws UsuarioNoEncontradoException {
        UserInfo usuario = buscarUsuarioPorId(userId);
        return usuario.getCarrito().getItems();
    }

    // Vaciar carrito
    public void vaciarCarrito(int userId) throws UsuarioNoEncontradoException {
        UserInfo usuario = buscarUsuarioPorId(userId);
        usuario.getCarrito().vaciarCarrito();
        usuarioRepository.save(usuario);
    }

    // Confirmar carrito y genera un pedido
    public Pedido confirmarCarrito(int userId) throws Exception, CarritoVacioException {
        UserInfo usuario = buscarUsuarioPorId(userId);

        List<LineaCarrito> items = usuario.getCarrito().getItems();
        if (items.isEmpty())
            throw new CarritoVacioException("El carrito está vacío.");

        // Validar stock
        for (LineaCarrito item : items) {
            if (item.getCantidad() > item.getProducto().getStock()) {
                throw new StockInsuficienteException("Stock insuficiente para " + item.getProducto().getNombre());
            }
        }

        int total = 0;
        for (LineaCarrito item : items) {
            ProductoInfo producto = item.getProducto();
            producto.setStock(producto.getStock() - item.getCantidad());
            total += producto.getPrecio() * item.getCantidad();
        }

        // Guardar los productos actualizados
        productoRepository.saveAll(items.stream().map(LineaCarrito::getProducto).toList());

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setItems(new ArrayList<>(items));
        pedido.setTotal(total);

        usuario.agregarPedido(pedido);
        usuario.getCarrito().vaciarCarrito();

        usuarioRepository.save(usuario);

        return pedido;
    }
}