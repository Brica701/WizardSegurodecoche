package com.example.wizardsegurodecoche.Controller;

import com.example.wizardsegurodecoche.DTO.WizardDataDTO;
import com.example.wizardsegurodecoche.Service.WizardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequestMapping("/wizard")
@SessionAttributes("wizardData")
public class WizardController {

    private final WizardService wizardService;

    public WizardController(WizardService wizardService) {
        this.wizardService = wizardService;
    }

    // ------------------------------------------------
    // Atributo de sesión para el wizard
    // ------------------------------------------------
    @ModelAttribute("wizardData")
    public WizardDataDTO wizardData() {
        return new WizardDataDTO();
    }

    // ------------------------------------------------
    // Mensaje simple estilo demoth1
    // ------------------------------------------------
    @GetMapping("/mensaje")
    public String mensajeSimple(Model model) {
        model.addAttribute("parrafo", "Bienvenido al Wizard de Seguro de Coche");
        return "wizard/mensaje";
    }

    // ------------------------------------------------
    // Paso 1: Crear nueva cotización (datos conductor)
    // ------------------------------------------------
    @GetMapping("/crear")
    public String crearCotizacion(Model model) {
        WizardDataDTO wizardData = new WizardDataDTO();
        model.addAttribute("wizardData", wizardData);
        return "wizard/paso1";
    }

    @PostMapping("/crear")
    public String crearCotizacionPost(@Valid @ModelAttribute("wizardData") WizardDataDTO wd,
                                      BindingResult br) {
        if (br.hasErrors()) return "wizard/paso1";
        return "redirect:/wizard/paso2";
    }

    // ------------------------------------------------
    // Paso 2: Datos vehículo
    // ------------------------------------------------
    @GetMapping("/paso2")
    public String paso2() {
        return "wizard/paso2";
    }

    @PostMapping("/paso2")
    public String paso2Post(@Valid @ModelAttribute("wizardData") WizardDataDTO wd,
                            BindingResult br) {
        if (br.hasErrors()) return "wizard/paso2";
        return "redirect:/wizard/paso3";
    }

    // ------------------------------------------------
    // Paso 3: Coberturas y resumen
    // ------------------------------------------------
    @GetMapping("/paso3")
    public String paso3(@ModelAttribute("wizardData") WizardDataDTO wd, Model model) {
        BigDecimal precio = wizardService.calcularPrecio(wd);
        wd.setPrecioTotal(precio);
        model.addAttribute("precioCalculadoStr", precio.setScale(2, RoundingMode.HALF_UP).toPlainString());
        return "wizard/paso3";
    }

    @PostMapping("/confirmar")
    public String confirmar(@ModelAttribute("wizardData") WizardDataDTO wd,
                            Model model, HttpSession session) {
        BigDecimal precio = wizardService.calcularPrecio(wd);
        wd.setPrecioTotal(precio);
        wizardService.guardarCotizacion(wd);

        model.addAttribute("mensaje", "Cotización guardada correctamente!");
        session.setAttribute("ultimoId", wd.getNombre()); // ejemplo de valor en sesión
        return "wizard/confirmacion";
    }

    // ------------------------------------------------
    // Listar todas las cotizaciones
    // ------------------------------------------------
    @GetMapping("/listar")
    public String listarCotizaciones(Model model) {
        List<WizardDataDTO> lista = wizardService.all();
        model.addAttribute("cotizaciones", lista);
        return "wizard/listar";
    }

}
