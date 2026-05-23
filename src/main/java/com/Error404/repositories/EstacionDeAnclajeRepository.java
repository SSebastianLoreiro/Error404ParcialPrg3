package com.Error404.repositories;

import com.Error404.models.EstacionDeAnclaje;
import org.springframework.stereotype.Repository;

@Repository
public class EstacionDeAnclajeRepository extends BaseRepository<EstacionDeAnclaje, String> {

    @Override
    protected String getKey(EstacionDeAnclaje entity) {
        return entity.getNombreUnico();
    }

    public EstacionDeAnclaje save(EstacionDeAnclaje estacion) {
        return super.save(estacion);
    }
}
