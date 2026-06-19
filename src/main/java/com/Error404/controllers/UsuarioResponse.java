package com.Error404.controllers;

public class UsuarioResponse {
    private String id;
    private String nombreCompleto;

    public UsuarioResponse(String id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public String getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
}
