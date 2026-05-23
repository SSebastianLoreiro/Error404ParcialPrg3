package com.Error404.repositories;

import com.Error404.models.ProcesamientoDePagos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ProcesamientoDePagosRepository {

    private final Map<String, ProcesamientoDePagos> storage = new LinkedHashMap<>();

    public String save(ProcesamientoDePagos pago) {
        if (pago == null) {
            return null;
        }
        String key = UUID.randomUUID().toString();
        storage.put(key, pago);
        return key;
    }

    public Optional<ProcesamientoDePagos> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<ProcesamientoDePagos> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }

    public void deleteAll() {
        storage.clear();
    }

    public long count() {
        return storage.size();
    }
}
