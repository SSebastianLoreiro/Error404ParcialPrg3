package com.Error404.services;

import com.Error404.models.Usuario;
import com.Error404.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public Optional<Usuario> findById(String id) {
        return repository.findById(id);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }

    public Optional<Double> calcularCosto(String id, double tarifaVehiculo) {
        Optional<Usuario> opt = findById(id);
        if (opt.isPresent()) {
            return Optional.of(opt.get().calcularCosto(tarifaVehiculo));
        }
        return Optional.empty();
    }

    public Optional<String> getTipo(String id) {
        Optional<Usuario> opt = findById(id);
        if (opt.isPresent()) {
            return Optional.of(opt.get().getTipo());
        }
        return Optional.empty();
    }
}
