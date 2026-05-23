package com.Error404.repositories;

import com.Error404.models.BicicletaElectrica;
import org.springframework.stereotype.Repository;

@Repository
public class BicicletaElectricaRepository extends BaseRepository<BicicletaElectrica, String> {

    @Override
    protected String getKey(BicicletaElectrica entity) {
        return entity.getNumPatente();
    }

    public BicicletaElectrica save(BicicletaElectrica bicicletaElectrica) {
        return super.save(bicicletaElectrica);
    }
}
