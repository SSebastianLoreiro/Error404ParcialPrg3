package com.Error404.repositories;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseRepository<T, K> {
    protected final Map<K, T> storage = new LinkedHashMap<>();

    protected abstract K getKey(T entity);

    public T save(T entity) {
        if (entity == null) {
            return null;
        }
        storage.put(getKey(entity), entity);
        return entity;
    }

    public Optional<T> findById(K id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean existsById(K id) {
        return storage.containsKey(id);
    }

    public boolean deleteById(K id) {
        return storage.remove(id) != null;
    }

    public void deleteAll() {
        storage.clear();
    }

    public long count() {
        return storage.size();
    }
}
