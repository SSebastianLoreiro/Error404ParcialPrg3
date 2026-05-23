package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Usuario {
    private String id;
    private String nombre_completo;

    public Usuario(String id, String nombre_completo) {
        this.id = id;
        this.nombre_completo = nombre_completo;
    }
}

class UsuarioRegular extends Usuario {
    private double costoEstandart;

    public UsuarioRegular(String id, String nombre_completo, double costoEstandart) {
        super(id, nombre_completo);
        this.costoEstandart = costoEstandart;
    }
}

class UsuarioPremium extends Usuario {
    private double costoPremium;

    public UsuarioPremium(String id, String nombre_completo, double costoPremium) {
        super(id, nombre_completo);
        this.costoPremium = costoPremium;
    }
}
