package com.Error404.repositories;

import com.Error404.models.Vehiculo;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoRepository extends BaseRepository<Vehiculo, String> {

    @Override
    protected String getKey(Vehiculo entity) {
        return entity.getNumPatente();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return super.save(vehiculo);
    }
}
