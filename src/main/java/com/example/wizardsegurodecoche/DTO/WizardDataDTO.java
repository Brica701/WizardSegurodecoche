package com.example.wizardsegurodecoche.DTO;


import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WizardDataDTO {

    // Datos del conductor
    @NotBlank
    private String nombre;

    @NotNull @Min(18)
    private Integer edad;

    @NotNull @Min(0)
    private Integer aniosCarnet;

    // Datos del vehículo
    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    private Integer anioMat;

    @NotBlank
    private String uso; // "PRIVADO" o "PROFESIONAL"

    // Coberturas
    @NotBlank
    private String tipoCobertura; // "TERCEROS","TERCEROS_LUNAS","TODO_RIESGO"

    private boolean asistencia;
    private boolean vehSustitucion;

    @NotNull
    @DecimalMin("0.0")
    // Precio final (BigDecimal)
    private BigDecimal precioTotal;

}
