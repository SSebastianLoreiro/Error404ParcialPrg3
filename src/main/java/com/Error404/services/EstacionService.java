package com.Error404.services;

import com.Error404.models.EstacionDeAnclaje;
import com.Error404.models.Vehiculo;
import com.Error404.repositories.EstacionDeAnclajeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EstacionService {
    private final EstacionDeAnclajeRepository repository;
    private final Map<String, Vehiculo> vehiculosPorPatente = new HashMap<>();

    public EstacionService(EstacionDeAnclajeRepository repository) {
        this.repository = repository;
    }

    public EstacionDeAnclaje save(EstacionDeAnclaje estacion) {
        if (estacion != null && estacion.getVehiculosDisponibles() != null) {
            for (Vehiculo vehiculo : estacion.getVehiculosDisponibles()) {
                if (vehiculo != null && vehiculo.getNumPatente() != null) {
                    vehiculosPorPatente.put(vehiculo.getNumPatente(), vehiculo);
                }
            }
        }
        return repository.save(estacion);
    }

    public Optional<EstacionDeAnclaje> findByNombre(String nombreUnico) {
        return repository.findById(nombreUnico);
    }

    public List<EstacionDeAnclaje> findAll() {
        return repository.findAll();
    }

    public List<Vehiculo> findAllVehiculos() {
        return new ArrayList<>(vehiculosPorPatente.values());
    }

    public Optional<Vehiculo> buscarVehiculoPorPatente(String patente) {
        if (patente == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(vehiculosPorPatente.get(patente));
    }

    public boolean deleteByNombre(String nombreUnico) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreUnico);
        if (optionalEstacion.isPresent()) {
            EstacionDeAnclaje estacion = optionalEstacion.get();
            if (estacion.getVehiculosDisponibles() != null) {
                for (Vehiculo vehiculo : estacion.getVehiculosDisponibles()) {
                    if (vehiculo != null) {
                        vehiculosPorPatente.remove(vehiculo.getNumPatente());
                    }
                }
            }
        }
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
        vehiculosPorPatente.put(vehiculo.getNumPatente(), vehiculo);
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
            if (removed) {
                vehiculosPorPatente.remove(patente);
            }
            repository.save(estacion);
            return removed;
        }
        return false;
    }

    public Optional<Vehiculo> buscarPorPatente(EstacionDeAnclaje estacion, String patente) {
        if (estacion == null || patente == null) {
            return Optional.empty();
        }
        for (Vehiculo vehiculo : estacion.getVehiculosDisponibles()) {
            if (patente.equals(vehiculo.getNumPatente())) {
                return Optional.of(vehiculo);
            }
        }
        return Optional.empty();
    }

    public List<Vehiculo> listarVehiculosDisponibles(String nombreEstacion) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        if (optionalEstacion.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(optionalEstacion.get().getVehiculosDisponibles());
    }

    public int cantidadVehiculos(String nombreEstacion) {
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        if (optionalEstacion.isEmpty()) {
            return 0;
        }
        return optionalEstacion.get().getVehiculosDisponibles().size();
    }

    public boolean tieneVehiculos(String nombreEstacion) {
        return cantidadVehiculos(nombreEstacion) > 0;
    }

    public List<Vehiculo> obtenerPorTipo(String nombreEstacion, Class<? extends Vehiculo> tipo) {
        List<Vehiculo> resultado = new ArrayList<>();
        if (tipo == null) {
            return resultado;
        }
        Optional<EstacionDeAnclaje> optionalEstacion = findByNombre(nombreEstacion);
        if (optionalEstacion.isEmpty()) {
            return resultado;
        }
        for (Vehiculo vehiculo : optionalEstacion.get().getVehiculosDisponibles()) {
            if (tipo.isAssignableFrom(vehiculo.getClass())) {
                resultado.add(vehiculo);
            }
        }
        return resultado;
    }

    public Optional<EstacionDeAnclaje> buscarEstacionPorVehiculo(String patente) {
        if (patente == null) {
            return Optional.empty();
        }
        for (EstacionDeAnclaje estacion : findAll()) {
            if (buscarPorPatente(estacion, patente).isPresent()) {
                return Optional.of(estacion);
            }
        }
        return Optional.empty();
    }

    public Optional<Vehiculo> buscarVehiculoEnTodasLasEstaciones(String patente) {
        return buscarVehiculoPorPatente(patente);
    }
}
