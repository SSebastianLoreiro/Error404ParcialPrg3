package com.Error404.models;

import java.util.List;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EstacionDeAnclaje {
   private String nombreUnico;
   private List<Vehiculo> vehiculosDisponibles;

   public EstacionDeAnclaje(String nombreUnico) {
       this.nombreUnico = nombreUnico;
       this.vehiculosDisponibles = new ArrayList<>();
   }
}

