# Anexo técnico de rendimiento y diseño

Este documento fusiona el resumen de decisiones de diseño y el análisis asintótico (Big O) aplicado en la implementación de EcoRide Pro. Contiene justificaciones técnicas, cambios realizados y recomendaciones operativas.

## 1) Acceso instantáneo a vehículos por patente

Problema anterior: búsqueda lineal en colecciones (ArrayList) → O(N) en peor caso.

Solución: índice por patente con `HashMap<String, Vehiculo>` dentro de `EstacionService`.

- Complejidad temporal (promedio): O(1) por búsqueda por patente.
- Justificación: evita recorridos secuenciales costosos y escala de forma estable con flotas grandes.

Implementación: mantener `vehiculosPorPatente` sincronizado en los métodos de alta/baja del servicio para garantizar consultas O(1).

## 2) Deduplicación de alertas GPS (una pasada)

Problema: listas con miles de coordenadas duplicadas por fallas de hardware.

Restricciones: usar bucle imperativo clásico, prohibido Streams/lambdas, evitar bucles anidados.

Solución: `HashSet<GpsReporte>` + una iteración `for` que inserta y filtra duplicados.

- Complejidad temporal: O(N).
- Complejidad espacial: O(N) adicional para el conjunto en el peor caso.

Tabla comparativa (resumen):

- Fuerza bruta (doble bucle): O(N^2) — inaceptable para >10k reportes.
- HashSet (una pasada): O(N) — recomendado y usado.

Implementación: método `AlertaGpsService.deduplicarAlertas(List<GpsReporte> reportes)` que devuelve una lista sin duplicados manteniendo la política de memoria imperativa.

## 3) Ordenamiento de flota

Requerimientos:
- Orden natural por batería (menor → mayor) para prioridad de carga.
- Orden alternativo por tarifa base (mayor → menor) sin alterar la colección base.

Solución:

- `Vehiculo` implementa `Comparable<Vehiculo>` comparando `porcentajeBateria`.
- `VehiculoTarifaDescendenteComparator` implementa `Comparator<Vehiculo>` para vista alternativa.
- Cuando se necesita la vista alternativa se crea una copia superficial de la lista y se aplica `Collections.sort(listaCopia, new VehiculoTarifaDescendenteComparator())`.

Complejidad temporal: O(N log N) (Timsort) en ambos casos.

## 4) Control de ciclo de vida: patrón State

Problema: transiciones complejas entre fases del vehículo con condicionales anidados.

Solución: patrón State con clases concretas que modelan estados:
- `EnEsperaEstado`
- `EnViajeEstado`
- `EnReparacionEstado`

Cada estado valida sus transiciones y lanza excepciones en caso de acciones no permitidas. Esto mejora mantenibilidad y evita condicionales dispersos.

## 5) Tarifas dinámicas en tiempo de ejecución: patrón Strategy

Estrategias implementadas y registradas como beans Spring (configurable en caliente):
- `TarifaEstandarStrategy`
- `TarifaHoraPicoStrategy`
- `TarifaClimaticaStrategy`

`CriterioTarifaService` inyecta las estrategias y permite cambiar el criterio activo mediante el endpoint `/api/alquileres/criterio` (ahora POST con JSON).

## 6) Casos de uso y ejemplos

- Desbloqueo: validación de batería mínima y cálculo estimado con el criterio activo.
- Finalizar alquiler: validación de estado `En Viaje`, cálculo por minutos y registro de pago.

## 7) Recomendaciones operativas

- Mantener las estructuras de índice (`vehiculosPorPatente`) sincronizadas en todas las rutas de modificación de la flota para preservar O(1).
- Para grandes volúmenes de GPS, considerar un procesamiento por lotes asíncrono fuera de la ruta HTTP si la entrada supera decenas de miles por petición.
