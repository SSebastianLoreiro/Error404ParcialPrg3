package com.Error404.controllers;

import com.Error404.models.Usuario;
import com.Error404.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

 

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioResponse> respuesta = new ArrayList<>();
        for (Usuario u : usuarios) {
            respuesta.add(new UsuarioResponse(u.getId(), u.getNombre_completo()));
        }
        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable String id) {
        java.util.Optional<Usuario> optional = usuarioService.findById(id);
        if (optional.isPresent()) {
            Usuario u = optional.get();
            return ResponseEntity.ok(new UsuarioResponse(u.getId(), u.getNombre_completo()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public UsuarioResponse crearUsuario(@RequestBody UsuarioRequest req) {
        Usuario u = new Usuario(req.getId(), req.getNombreCompleto());
        Usuario saved = usuarioService.save(u);
        return new UsuarioResponse(saved.getId(), saved.getNombre_completo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        return usuarioService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
