package com.Error404.services;

import com.Error404.models.Vehiculo;
import com.Error404.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    private final VehiculoRepository repository;

    public VehiculoService(VehiculoRepository repository) {
        this.repository = repository;
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return repository.save(vehiculo);
    }

    public Optional<Vehiculo> findByPatente(String patente) {
        return repository.findById(patente);
    }

    public List<Vehiculo> findAll() {
        return repository.findAll();
    }

    public boolean deleteByPatente(String patente) {
        return repository.deleteById(patente);
    }

    public boolean existsByPatente(String patente) {
        return repository.existsById(patente);
    }

    public Optional<Double> calcularTarifa(String patente) {
        return findByPatente(patente).map(Vehiculo::calcularTarifa);
    }

    public boolean consumirBateria(String patente, int porcentaje) {
        Optional<Vehiculo> optional = findByPatente(patente);
        if (optional.isPresent()) {
            Vehiculo vehiculo = optional.get();
            vehiculo.consumirBateria(porcentaje);
            repository.save(vehiculo);
            return true;
        }
        return false;
    }

    public boolean cargarBateria(String patente, int porcentaje) {
        Optional<Vehiculo> optional = findByPatente(patente);
        if (optional.isPresent()) {
            Vehiculo vehiculo = optional.get();
            vehiculo.cargarBateria(porcentaje);
            repository.save(vehiculo);
            return true;
        }
        return false;
    }
}
