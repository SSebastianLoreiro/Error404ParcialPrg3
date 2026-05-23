package com.Error404.services;

import com.Error404.models.ProcesamientoDePagos;
import com.Error404.repositories.ProcesamientoDePagosRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcesamientoDePagosService {
    private final ProcesamientoDePagosRepository repository;

    public ProcesamientoDePagosService(ProcesamientoDePagosRepository repository) {
        this.repository = repository;
    }

    public String save(ProcesamientoDePagos pago) {
        return repository.save(pago);
    }

    public Optional<ProcesamientoDePagos> findById(String id) {
        return repository.findById(id);
    }

    public List<ProcesamientoDePagos> findAll() {
        return repository.findAll();
    }

    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
