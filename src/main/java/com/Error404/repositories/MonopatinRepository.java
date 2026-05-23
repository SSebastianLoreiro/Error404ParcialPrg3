package com.Error404.repositories;

import com.Error404.models.Monopatin;
import org.springframework.stereotype.Repository;

@Repository
public class MonopatinRepository extends BaseRepository<Monopatin, String> {

    @Override
    protected String getKey(Monopatin entity) {
        return entity.getNumPatente();
    }

    public Monopatin save(Monopatin monopatin) {
        return super.save(monopatin);
    }
}
