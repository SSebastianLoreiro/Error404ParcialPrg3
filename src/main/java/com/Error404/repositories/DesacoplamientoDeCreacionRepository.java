package com.Error404.repositories;

import com.Error404.models.DesacoplamientoDeCreacion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class DesacoplamientoDeCreacionRepository {

    private final Map<String, DesacoplamientoDeCreacion> storage = new LinkedHashMap<>();

    public String save(DesacoplamientoDeCreacion entity) {
        if (entity == null) {
            return null;
        }
        String key = UUID.randomUUID().toString();
        storage.put(key, entity);
        return key;
    }

    public Optional<DesacoplamientoDeCreacion> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<DesacoplamientoDeCreacion> findAll() {
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
