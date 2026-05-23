package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProcesamientoDePagos {
    public enum TipoDePago {
        TARJETA_CREDITO,
        BILLETERA_VIRTUAL;

        public static TipoDePago fromString(String value) {
            if (value == null) {
                return null;
            }
            String normalized = value.trim().toUpperCase().replace('-', '_').replace(' ', '_');
            try {
                return TipoDePago.valueOf(normalized);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    private String usuarioId;
    private String patente;
    private TipoDePago metodoPago;
    private double monto;

    public ProcesamientoDePagos() {
    }

    public ProcesamientoDePagos(String usuarioId, String patente, TipoDePago metodoPago, double monto) {
        this.usuarioId = usuarioId;
        this.patente = patente;
        this.metodoPago = metodoPago;
        this.monto = monto;
    }
}
