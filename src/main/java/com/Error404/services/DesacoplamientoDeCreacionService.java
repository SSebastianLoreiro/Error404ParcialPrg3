package com.Error404.services;

import com.Error404.models.DesacoplamientoDeCreacion;
import com.Error404.repositories.DesacoplamientoDeCreacionRepository;

import java.util.List;
import java.util.Optional;

public class DesacoplamientoDeCreacionService {
    private final DesacoplamientoDeCreacionRepository repository;

    public DesacoplamientoDeCreacionService(DesacoplamientoDeCreacionRepository repository) {
        this.repository = repository;
    }

    public String save(DesacoplamientoDeCreacion entity) {
        return repository.save(entity);
    }

    public Optional<DesacoplamientoDeCreacion> findById(String id) {
        return repository.findById(id);
    }

    public List<DesacoplamientoDeCreacion> findAll() {
        return repository.findAll();
    }

    public boolean deleteById(String id) {
        return repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
