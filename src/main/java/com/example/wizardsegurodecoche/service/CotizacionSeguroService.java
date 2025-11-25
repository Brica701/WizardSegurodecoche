package com.example.wizardsegurodecoche.service;

import com.example.wizardsegurodecoche.model.CotizacionSeguro;
import com.example.wizardsegurodecoche.repository.CotizacionSeguroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CotizacionSeguroService {

    private final CotizacionSeguroRepository repo;

    public BigDecimal calcularPrecio(CotizacionSeguro c) {

        BigDecimal precio = BigDecimal.valueOf(300); // base fija

        if (c.getEdad() < 25) {
            precio = precio.add(BigDecimal.valueOf(120));
        }

        if (c.getAnios_carnet() < 3) {
            precio = precio.add(BigDecimal.valueOf(90));
        }

        if ("PROFESIONAL".equalsIgnoreCase(c.getUso())) {
            precio = precio.add(BigDecimal.valueOf(80));
        }

        switch (c.getTipo_cobertura().toUpperCase()) {
            case "TERCEROS" -> precio = precio.add(BigDecimal.valueOf(0));
            case "TERCEROS_LUNAS" -> precio = precio.add(BigDecimal.valueOf(50));
            case "TODO_RIESGO" -> precio = precio.add(BigDecimal.valueOf(200));
            default -> precio = precio.add(BigDecimal.valueOf(0));
        }

        if (c.isAsistencia()) {
            precio = precio.add(BigDecimal.valueOf(25));
        }

        if (c.isVeh_sustitucion()) {
            precio = precio.add(BigDecimal.valueOf(40));
        }

        return precio;
    }

    public Long guardarCotizacion(CotizacionSeguro c) {
        return repo.guardar(c);
    }
}