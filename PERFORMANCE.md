# Performance and Design Notes

## Búsqueda instantánea de vehículos por patente
La nueva implementación utiliza un `HashMap<String, Vehiculo>` dentro de `EstacionService` para indexar los vehículos cuando se añaden a una estación. Esto transforma la búsqueda de patente de un recorrido lineal en todas las estaciones a una consulta de acceso directo en tiempo constante O(1), eliminando el costo de recorrer cientos o miles de listas.

## Deduplicación de alertas de GPS
La deduplicación se implementó en `AlertaGpsService.deduplicarAlertas(List<GpsReporte> reportes)` usando un `HashSet<GpsReporte>` y una sola pasada sobre la lista original. Cada reporte se agrega y comprueba una sola vez, lo que evita bucles anidados y mantiene la complejidad en O(n).

## Ordenamiento natural y comparador externo
- El ordenamiento natural de los vehículos se definió en `Vehiculo.compareTo(Vehiculo otra)` comparando el porcentaje de batería de menor a mayor. Esto respeta la prioridad de carga operativa.
- El criterio alternativo de tarifa base se creó mediante `VehiculoTarifaDescendenteComparator`, un comparador externo. De este modo, la comparación intrínseca de batería no se modifica y se puede aplicar dinámicamente solo cuando se solicita la vista de tarifa descendente.

## Control de ciclo de vida del vehículo
La gestión de estados se resolvió con el patrón State:
- `EnEsperaEstado`
- `EnViajeEstado`
- `EnReparacionEstado`

Cada estado conoce sus propias transiciones válidas y rechaza acciones inválidas con excepciones. Esto evita estructuras `if/else` voluminosas y permite agregar futuras fases sin cambiar la lógica de `Vehiculo`.

## Tarifas dinámicas en tiempo de ejecución
Se implementó el patrón Strategy en `CriterioTarifaService` con las siguientes estrategias:
- `TarifaEstandarStrategy`
- `TarifaHoraPicoStrategy`
- `TarifaClimaticaStrategy`

El criterio activo puede cambiarse en caliente mediante el endpoint `/api/alquileres/criterio?tipo=...`.
