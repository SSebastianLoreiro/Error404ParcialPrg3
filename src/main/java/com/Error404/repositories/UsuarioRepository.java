package com.Error404.repositories;

import com.Error404.models.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository extends BaseRepository<Usuario, String> {

    @Override
    protected String getKey(Usuario entity) {
        return entity.getId();
    }

    public Usuario save(Usuario usuario) {
        return super.save(usuario);
    }
}
