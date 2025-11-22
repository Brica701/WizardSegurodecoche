package com.example.wizardsegurodecoche.Service;

import com.example.wizardsegurodecoche.DAO.CotizacionDAO;
import com.example.wizardsegurodecoche.DTO.WizardDataDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class WizardService {

    private final CotizacionDAO cotizacionDAO;

    public WizardService(CotizacionDAO cotizacionDAO) {
        this.cotizacionDAO = cotizacionDAO;
    }

    // Obtener todas las cotizaciones
    public List<WizardDataDTO> all() {
        return cotizacionDAO.getAll();
    }

    // Guardar una nueva cotización
    public void guardarCotizacion(WizardDataDTO w) {
        cotizacionDAO.create(w);
    }

    // Buscar por id
    public WizardDataDTO obtenerPorId(long id) {
        return cotizacionDAO.find(id).orElse(null);
    }

    // Actualizar
    public void actualizarCotizacion(WizardDataDTO w) {
        cotizacionDAO.update(w);
    }

    // Eliminar
    public void eliminarCotizacion(long id) {
        cotizacionDAO.delete(id);
    }

    // Calcular precio según reglas
    public BigDecimal calcularPrecio(WizardDataDTO w) {
        BigDecimal precio = new BigDecimal("200.00"); // base

        if (w.getEdad() < 25) precio = precio.add(new BigDecimal("120.00"));
        else if (w.getEdad() < 30) precio = precio.add(new BigDecimal("40.00"));

        if (w.getAniosCarnet() < 2) precio = precio.add(new BigDecimal("100.00"));
        else if (w.getAniosCarnet() < 5) precio = precio.add(new BigDecimal("40.00"));

        if ("PROFESIONAL".equalsIgnoreCase(w.getUso())) precio = precio.add(new BigDecimal("80.00"));

        switch (w.getTipoCobertura()) {
            case "TERCEROS_LUNAS" -> precio = precio.add(new BigDecimal("50.00"));
            case "TODO_RIESGO" -> precio = precio.add(new BigDecimal("300.00"));
        }

        if (w.isAsistencia()) precio = precio.add(new BigDecimal("20.00"));
        if (w.isVehSustitucion()) precio = precio.add(new BigDecimal("60.00"));

        int edadVeh = java.time.Year.now().getValue() - w.getAnioMat();
        if (edadVeh > 10) precio = precio.subtract(new BigDecimal("20.00"));

        return precio.setScale(2, RoundingMode.HALF_UP);
    }
}
