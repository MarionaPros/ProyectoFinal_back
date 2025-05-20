package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.controller;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.*;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Carrito;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.CarritoItem;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Usuario;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.CarritoRequest;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model.Role;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.CarritoItemRepository;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.TokenRepositorio;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CarritoController {

        @Autowired
        private UsuarioRepositorio usuarioRepositorio;

        @Autowired
        private TokenRepositorio tokenRepositorio;

        @Autowired
        private CarritoItemRepository carritoItemRepositorio;

        @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
        @PostMapping("/api/carrito")
        public ResponseEntity<?> añadirAlCarrito(@CookieValue(value = "session", required = false) String tokenId,
                                                 @RequestBody CarritoRequest request) {

            if (tokenId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debes iniciar sesión");
            }

            Token token = tokenRepositorio.findById(tokenId).orElse(null);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sesión no válida");
            }

            Usuario usuario = token.getUsuario();
            Carrito carrito = usuario.getCarrito();
            if (carrito == null) {
                // Si por alguna razón no se creó el carrito al registrarse, lo creamos aquí
                carrito = new Carrito();
                carrito.setUsuario(usuario);
                usuario.setCarrito(carrito);
                usuarioRepositorio.save(usuario);
            }

            // Creamos el item con datos del producto externo
            CarritoItem item = new CarritoItem();
            item.setCarrito(carrito);
            item.setProductoApiId(request.productoId());
            item.setNombre(request.nombre());
            item.setImagenUrl(request.imagenUrl());
            item.setPrecio(request.precio());
            item.setCantidad(request.cantidad());

            carritoItemRepositorio.save(item);

            return ResponseEntity.ok().build();
        }
    @GetMapping("/api/carrito")
    @CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
    public ResponseEntity<?> obtenerCarrito(@CookieValue(value = "session", required = false) String tokenId) {
        if (tokenId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Token token = tokenRepositorio.findById(tokenId).orElse(null);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = token.getUsuario();

        if (usuario.getRol() == Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Los administradores no pueden acceder al carrito.");
        }

        Carrito carrito = token.getUsuario().getCarrito();

        List<Map<String, Object>> items = carrito.getItems().stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombre", item.getNombre());
            map.put("imagenUrl", item.getImagenUrl());
            map.put("precio", item.getPrecio());
            map.put("cantidad", item.getCantidad());
            return map;
        }).toList();

        return ResponseEntity.ok(items);
    }

}

