package com.Error404;

import com.Error404.models.BicicletaElectrica;
import com.Error404.models.EstacionDeAnclaje;
import com.Error404.models.Monopatin;
import com.Error404.models.Usuario;
import com.Error404.services.BicicletaElectricaService;
import com.Error404.services.EstacionService;
import com.Error404.services.MonopatinService;
import com.Error404.services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final EstacionService estacionService;
    private final MonopatinService monopatinService;
    private final BicicletaElectricaService bicicletaService;

    public DataLoader(UsuarioService usuarioService,
                      EstacionService estacionService,
                      MonopatinService monopatinService,
                      BicicletaElectricaService bicicletaService) {
        this.usuarioService = usuarioService;
        this.estacionService = estacionService;
        this.monopatinService = monopatinService;
        this.bicicletaService = bicicletaService;
    }

    @Override
    public void run(String... args) {
        cargarUsuarios();
        cargarEstacionesYVehiculos();
    }

    private void cargarUsuarios() {
        usuarioService.save(new Usuario("u1", "Juan Perez"));
        usuarioService.save(new Usuario("u2", "María Premium"));
        usuarioService.save(new Usuario("u3", "Luis Regular"));
    }

    private void cargarEstacionesYVehiculos() {
        EstacionDeAnclaje estacionCentro = new EstacionDeAnclaje("EstacionCentro");
        EstacionDeAnclaje estacionNorte = new EstacionDeAnclaje("EstacionNorte");

        estacionService.save(estacionCentro);
        estacionService.save(estacionNorte);

        Monopatin monopatin1 = new Monopatin("MOTO123", 85, 10.0, true);
        Monopatin monopatin2 = new Monopatin("MOTO456", 30, 9.0, false);
        BicicletaElectrica bicicleta1 = new BicicletaElectrica("BICI321", 65, 8.0, 12.5);
        BicicletaElectrica bicicleta2 = new BicicletaElectrica("BICI654", 15, 7.0, 0.0);

        monopatinService.save(monopatin1);
        monopatinService.save(monopatin2);
        bicicletaService.save(bicicleta1);
        bicicletaService.save(bicicleta2);

        estacionService.agregarVehiculoAEstacion("EstacionCentro", monopatin1);
        estacionService.agregarVehiculoAEstacion("EstacionCentro", bicicleta1);
        estacionService.agregarVehiculoAEstacion("EstacionNorte", monopatin2);
        estacionService.agregarVehiculoAEstacion("EstacionNorte", bicicleta2);
    }
}
