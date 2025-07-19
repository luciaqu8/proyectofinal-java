package com.techlab.proyectofinal_java.controller;

import com.techlab.proyectofinal_java.exceptions.CantidadInvalidaException;
import com.techlab.proyectofinal_java.exceptions.StockNegativoException;
import com.techlab.proyectofinal_java.models.productos.ProductoInfo;
import com.techlab.proyectofinal_java.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/new")
    public ResponseEntity<?> agregarProducto(@RequestBody ProductoInfo producto) {
        try {
            producto.validar(); // Tu método personalizado
            ProductoInfo guardado = productoService.agregarProducto(producto);
            return ResponseEntity.ok(guardado);

        } catch (CantidadInvalidaException | StockNegativoException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductoInfo>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }


    @GetMapping("/findId/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            ProductoInfo producto = productoService.buscarPorId(id);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró el producto con ID: " + id);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody ProductoInfo nuevoProducto) {
        try {
            nuevoProducto.validar();
            ProductoInfo actualizado = productoService.actualizarProducto(id, nuevoProducto);
            return ResponseEntity.ok(actualizado);

        } catch (CantidadInvalidaException | StockNegativoException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminarProducto(
            @PathVariable int id,
            @RequestParam(required = false, defaultValue = "false") boolean confirm) {

        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body("El ID debe ser un número mayor a cero.");
            }

            ProductoInfo productoExistente = productoService.buscarPorId(id);

            if (productoExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con ID " + id + " no encontrado.");
            }

            if (!confirm) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("¿Está seguro que desea eliminar el producto con ID " + id + "? Agregue '?confirm=true' para confirmar.");
            }

            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado correctamente.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al eliminar producto: " + e.getMessage());
        }
    }
}
