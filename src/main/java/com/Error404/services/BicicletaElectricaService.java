package com.Error404.services;

import com.Error404.models.BicicletaElectrica;
import com.Error404.repositories.BicicletaElectricaRepository;

import java.util.List;
import java.util.Optional;

public class BicicletaElectricaService {
    private final BicicletaElectricaRepository repository;

    public BicicletaElectricaService(BicicletaElectricaRepository repository) {
        this.repository = repository;
    }

    public BicicletaElectrica save(BicicletaElectrica bicicletaElectrica) {
        return repository.save(bicicletaElectrica);
    }

    public Optional<BicicletaElectrica> findByPatente(String patente) {
        return repository.findById(patente);
    }

    public List<BicicletaElectrica> findAll() {
        return repository.findAll();
    }

    public boolean deleteByPatente(String patente) {
        return repository.deleteById(patente);
    }

    public Optional<Double> calcularTarifa(String patente) {
        return findByPatente(patente).map(BicicletaElectrica::calcularTarifa);
    }

    public boolean setTamanoDeCanasto(String patente, double tamano) {
        Optional<BicicletaElectrica> optional = findByPatente(patente);
        if (optional.isPresent()) {
            BicicletaElectrica bicicleta = optional.get();
            bicicleta.setTamanoDeCanasto(tamano);
            repository.save(bicicleta);
            return true;
        }
        return false;
    }
}
