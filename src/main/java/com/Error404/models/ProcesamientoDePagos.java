package com.Error404.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProcesamientoDePagos {
    private enum TipoDePago {TARJETA_CREDITO, BILLETERA_VIRTUAL};
    private TipoDePago pagos;


    public ProcesamientoDePagos(TipoDePago pagos)
        { this.pagos = pagos;
     }

    
}
