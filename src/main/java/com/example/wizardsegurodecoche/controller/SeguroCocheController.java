package com.example.wizardsegurodecoche.controller;

import com.example.wizardsegurodecoche.model.CotizacionSeguro;
import com.example.wizardsegurodecoche.service.CotizacionSeguroService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class SeguroCocheController {

    private final CotizacionSeguroService servicio;

    public SeguroCocheController(CotizacionSeguroService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/paso1")
    public String paso1(Model model, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) {
            c = new CotizacionSeguro();
        }
        model.addAttribute("cotizacion", c);
        return "paso1";
    }

    @PostMapping("/paso1")
    public String procesarPaso1(@ModelAttribute CotizacionSeguro cotizacion, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) c = new CotizacionSeguro();

        c.setNombre(cotizacion.getNombre());
        c.setNif(cotizacion.getNif());
        c.setEdad(cotizacion.getEdad());
        c.setAnios_carnet(cotizacion.getAnios_carnet());

        session.setAttribute("cotizacion", c);
        return "redirect:/paso2";
    }

    @GetMapping("/paso2")
    public String paso2(Model model, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) return "redirect:/paso1";
        model.addAttribute("cotizacion", c);
        return "paso2";
    }

    @PostMapping("/paso2")
    public String procesarPaso2(@ModelAttribute CotizacionSeguro cotizacion, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) return "redirect:/paso1";

        c.setMarca(cotizacion.getMarca());
        c.setModelo(cotizacion.getModelo());
        c.setAnio_mat(cotizacion.getAnio_mat());
        c.setUso(cotizacion.getUso());

        session.setAttribute("cotizacion", c);
        return "redirect:/paso3";
    }

    @GetMapping("/paso3")
    public String paso3(Model model, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) return "redirect:/paso1";
        model.addAttribute("cotizacion", c);
        return "paso3";
    }

    @PostMapping("/confirmacion")
    public String confirmar(@ModelAttribute CotizacionSeguro cotizacion, Model model, HttpSession session) {
        CotizacionSeguro c = (CotizacionSeguro) session.getAttribute("cotizacion");
        if (c == null) return "redirect:/paso1";

        // Actualizar coberturas desde formulario
        c.setTipo_cobertura(cotizacion.getTipo_cobertura());
        c.setAsistencia(cotizacion.isAsistencia());
        c.setVeh_sustitucion(cotizacion.isVeh_sustitucion());

        // Calcular precio
        c.setPrecio_total(servicio.calcularPrecio(c));

        // Guardar en BD
        Long id = servicio.guardarCotizacion(c);

        model.addAttribute("id", id);
        model.addAttribute("precio", c.getPrecio_total());

        session.removeAttribute("cotizacion");

        return "confirmacion";
    }
}
