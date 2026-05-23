package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class DesacoplamientoDeCreacion {
    private ProcesamientoDePagos procespagos;

    public DesacoplamientoDeCreacion(ProcesamientoDePagos procespagos) {
        this.procespagos = procespagos;
    }
}
