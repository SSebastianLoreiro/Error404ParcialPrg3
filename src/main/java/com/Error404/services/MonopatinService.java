package com.Error404.services;

import com.Error404.models.Monopatin;
import com.Error404.repositories.MonopatinRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonopatinService {
    private final MonopatinRepository repository;

    public MonopatinService(MonopatinRepository repository) {
        this.repository = repository;
    }

    public Monopatin save(Monopatin monopatin) {
        return repository.save(monopatin);
    }

    public Optional<Monopatin> findByPatente(String patente) {
        return repository.findById(patente);
    }

    public List<Monopatin> findAll() {
        return repository.findAll();
    }

    public boolean deleteByPatente(String patente) {
        return repository.deleteById(patente);
    }

    public Optional<Double> calcularTarifa(String patente) {
        return findByPatente(patente).map(Monopatin::calcularTarifa);
    }

    public boolean setAmortiguacionReforzada(String patente, boolean valor) {
        Optional<Monopatin> optional = findByPatente(patente);
        if (optional.isPresent()) {
            Monopatin monopatin = optional.get();
            monopatin.setAmortiguacionReforzada(valor);
            repository.save(monopatin);
            return true;
        }
        return false;
    }
}
