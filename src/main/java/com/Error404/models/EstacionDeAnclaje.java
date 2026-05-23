package com.Error404.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;


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


   //buscar vehiculo en particular dentro del listado a traves de su patente
}
