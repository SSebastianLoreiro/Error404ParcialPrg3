package com.Error404.config;

import com.Error404.strategies.tarifa.TarifaClimaticaStrategy;
import com.Error404.strategies.tarifa.TarifaEstandarStrategy;
import com.Error404.strategies.tarifa.TarifaHoraPicoStrategy;
import com.Error404.strategies.tarifa.TarifaStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarifaConfig {

    @Bean("tarifaEstandarStrategy")
    public TarifaStrategy tarifaEstandarStrategy() {
        return new TarifaEstandarStrategy();
    }

    @Bean("tarifaHoraPicoStrategy")
    public TarifaStrategy tarifaHoraPicoStrategy() {
        return new TarifaHoraPicoStrategy();
    }

    @Bean("tarifaClimaticaStrategy")
    public TarifaStrategy tarifaClimaticaStrategy() {
        return new TarifaClimaticaStrategy();
    }
}
