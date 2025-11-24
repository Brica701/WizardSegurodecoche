package com.example.wizardsegurodecoche.repository;

import com.example.wizardsegurodecoche.model.CotizacionSeguro;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class CotizacionSeguroRepository {

    private final JdbcTemplate jdbcTemplate;

    public Long guardar(CotizacionSeguro c) {

        String sql = """
            INSERT INTO cotizacion_seguro 
            (nombre, nif, edad, anios_carnet, marca, modelo, anio_mat, uso, 
             tipo_cobertura, asistencia, veh_sustitucion, precio_total)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        KeyHolder kh = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getNif());
            ps.setInt(3, c.getEdad());
            ps.setInt(4, c.getAnios_carnet());
            ps.setString(5, c.getMarca());
            ps.setString(6, c.getModelo());
            ps.setInt(7, c.getAnio_mat());
            ps.setString(8, c.getUso());
            ps.setString(9, c.getTipo_cobertura());
            ps.setBoolean(10, c.isAsistencia());
            ps.setBoolean(11, c.isVeh_sustitucion());
            ps.setBigDecimal(12, c.getPrecio_total());
            return ps;
        }, kh);

        return kh.getKey().longValue();
    }
}