package com.Error404.models;

import java.util.Objects;

public class GpsReporte {
    private double latitud;
    private double longitud;

    public GpsReporte() {
    }

    public GpsReporte(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GpsReporte)) return false;
        GpsReporte that = (GpsReporte) o;
        return Double.compare(that.latitud, latitud) == 0 && Double.compare(that.longitud, longitud) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud);
    }
}
