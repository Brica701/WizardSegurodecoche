package com.example.wizardsegurodecoche.Controller;

import com.example.wizardsegurodecoche.model.CotizacionSeguro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/seguros")
@SessionAttributes("cotizacionSeguro")
public class SeguroController {

    // Se crea solo si no existe en sesión
    @ModelAttribute("cotizacionSeguro")
    public CotizacionSeguro cotizacionSession() {
        return new CotizacionSeguro();
    }

    // === PASO 1 GET ===
    @GetMapping("/calculos/cotizacion/paso1")
    public String paso1(@ModelAttribute("cotizacionSeguro") CotizacionSeguro cotizacionSeguro, Model model) {
        model.addAttribute("cotizacionSeguro", cotizacionSeguro);
        return "paso1";
    }

    // === PASO 2 GET ===
    @GetMapping("/calculos/cotizacion/paso2")
    public String paso2(@ModelAttribute("cotizacionSeguro") CotizacionSeguro cotizacionSeguro, Model model) {
        model.addAttribute("cotizacionSeguro", cotizacionSeguro);
        return "paso2";
    }

    // === PASO 2 POST ===
    @PostMapping("/calculos/cotizacion/paso2")
    public String paso2Post(@ModelAttribute("cotizacionSeguro") CotizacionSeguro cotizacionSeguro, Model model) {
        // Aqui NO editas nada, simplemente el objeto ya está en sesión
        model.addAttribute("cotizacionSeguro", cotizacionSeguro);
        return "paso2";
    }

    // === PASO 3 POST ===
    @PostMapping("/calculos/cotizacion/paso3")
    public String paso3Post(@ModelAttribute("cotizacionSeguro") CotizacionSeguro cotizacionSeguro, Model model) {

        // No se editan datos automáticamente, solo se muestran
        model.addAttribute("cotizacionSeguro", cotizacionSeguro);

        model.addAttribute("datosConductor",
                cotizacionSeguro.getNombre() + " | " +
                        cotizacionSeguro.getEdad() + " | " +
                        cotizacionSeguro.getAniosCarnet()
        );

        model.addAttribute("datosVehiculo",
                cotizacionSeguro.getMarca() + " | " +
                        cotizacionSeguro.getModelo() + " | " +
                        cotizacionSeguro.getAnioMat() + " | " +
                        cotizacionSeguro.getUso()
        );

        model.addAttribute("datosCobertura",
                cotizacionSeguro.getTipoCobertura() + " | " +
                        cotizacionSeguro.isAsistencia() + " | " +
                        cotizacionSeguro.isVehSustitucion()
        );

        return "paso3";
    }
}
