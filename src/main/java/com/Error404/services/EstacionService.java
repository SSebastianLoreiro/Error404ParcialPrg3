package com.Error404.services;

import com.Error404.models.EstacionDeAnclaje;
import com.Error404.models.Vehiculo;
import com.Error404.repositories.EstacionDeAnclajeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstacionService {
    private final EstacionDeAnclajeRepository repository;

    public EstacionService(EstacionDeAnclajeRepository repository) {
        this.repository = repository;
    }

    public EstacionDeAnclaje save(EstacionDeAnclaje estacion) {
        return repository.save(estacion);
    }

    public Optional<EstacionDeAnclaje> findByNombre(String nombreUnico) {
        return repository.findById(nombreUnico);
    }

    public List<EstacionDeAnclaje> findAll() {
        return repository.findAll();
    }

    public boolean deleteByNombre(String nombreUnico) {
        return repository.deleteById(nombreUnico);
    }

    public long count() {
        return repository.count();
    }

    public boolean agregarVehiculoAEstacion(String nombreEstacion, Vehiculo vehiculo) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        if (optionalEstacion.isEmpty() || vehiculo == null) {
            return false;
        }
        EstacionDeAnclaje estacion = optionalEstacion.get();
        Optional<Vehiculo> existente = buscarPorPatente(estacion, vehiculo.getNumPatente());
        if (existente.isPresent()) {
            return false;
        }
        estacion.getVehiculosDisponibles().add(vehiculo);
        repository.save(estacion);
        return true;
    }

    public boolean removerVehiculoDeEstacion(String nombreEstacion, String patente) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        if (optionalEstacion.isEmpty()) {
            return false;
        }
        EstacionDeAnclaje estacion = optionalEstacion.get();
        Optional<Vehiculo> encontrado = buscarPorPatente(estacion, patente);
        if (encontrado.isPresent()) {
            boolean removed = estacion.getVehiculosDisponibles().remove(encontrado.get());
            repository.save(estacion);
            return removed;
        }
        return false;
    }

    public Optional<Vehiculo> buscarPorPatente(EstacionDeAnclaje estacion, String patente) {
        if (estacion == null || patente == null) return Optional.empty();
        return estacion.getVehiculosDisponibles().stream()
                .filter(v -> patente.equals(v.getNumPatente()))
                .findFirst();
    }

    public List<Vehiculo> listarVehiculosDisponibles(String nombreEstacion) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        return optionalEstacion.map(estacion -> new ArrayList<>(estacion.getVehiculosDisponibles()))
                .orElseGet(ArrayList::new);
    }

    public int cantidadVehiculos(String nombreEstacion) {
        return findByNombre(nombreEstacion)
                .map(estacion -> estacion.getVehiculosDisponibles().size())
                .orElse(0);
    }

    public boolean tieneVehiculos(String nombreEstacion) {
        return cantidadVehiculos(nombreEstacion) > 0;
    }

    public List<Vehiculo> obtenerPorTipo(String nombreEstacion, Class<? extends Vehiculo> tipo) {
        if (tipo == null) return new ArrayList<>();
        return findByNombre(nombreEstacion)
                .map(estacion -> estacion.getVehiculosDisponibles().stream()
                        .filter(v -> tipo.isAssignableFrom(v.getClass()))
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }
}
