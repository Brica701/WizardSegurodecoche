package com.example.wizardsegurodecoche.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CotizacionSeguro {

    private Long id;
    private String nombre;
    private String nif;
    private int edad;
    private int anios_carnet;
    private String marca;
    private String modelo;
    private int anio_mat;
    private String uso;
    private boolean asistencia;
    private boolean veh_sustitucion;
    private BigDecimal precio_total;
    private String tipo_cobertura;

}
