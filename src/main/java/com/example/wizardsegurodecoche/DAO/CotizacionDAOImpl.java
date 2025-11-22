package com.example.wizardsegurodecoche.DAO;

import com.example.wizardsegurodecoche.DTO.WizardDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CotizacionDAOImpl implements CotizacionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(WizardDataDTO w) {
        String sql = """
                INSERT INTO cotizacion_seguro (
                    nombre, edad, anios_carnet, marca, modelo, anio_mat, uso, tipo_cobertura, asistencia, veh_sustitucion, precio_total
                ) VALUES (?,?,?,?,?,?,?,?,?,?,?)
                """;

        String[] ids = {"id"};
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, ids);
            ps.setString(1, w.getNombre());
            ps.setInt(2, w.getEdad());
            ps.setInt(3, w.getAniosCarnet());
            ps.setString(4, w.getMarca());
            ps.setString(5, w.getModelo());
            ps.setInt(6, w.getAnioMat());
            ps.setString(7, w.getUso());
            ps.setString(8, w.getTipoCobertura());
            ps.setBoolean(9, w.isAsistencia());
            ps.setBoolean(10, w.isVehSustitucion());
            ps.setBigDecimal(11, w.getPrecioTotal());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            // Si quieres, puedes guardar el id en DTO
            // w.setId(key.longValue());
        }
    }

    @Override
    public List<WizardDataDTO> getAll() {
        List<WizardDataDTO> list = jdbcTemplate.query(
                "SELECT * FROM cotizacion_seguro",
                (rs, rowNum) -> {
                    WizardDataDTO w = new WizardDataDTO();
                    w.setNombre(rs.getString("nombre"));
                    w.setEdad(rs.getInt("edad"));
                    w.setAniosCarnet(rs.getInt("anios_carnet"));
                    w.setMarca(rs.getString("marca"));
                    w.setModelo(rs.getString("modelo"));
                    w.setAnioMat(rs.getInt("anio_mat"));
                    w.setUso(rs.getString("uso"));
                    w.setTipoCobertura(rs.getString("tipo_cobertura"));
                    w.setAsistencia(rs.getBoolean("asistencia"));
                    w.setVehSustitucion(rs.getBoolean("veh_sustitucion"));
                    w.setPrecioTotal(rs.getBigDecimal("precio_total"));
                    return w;
                }
        );
        log.info("Devueltos {} registros.", list.size());
        return list;
    }

    @Override
    public Optional<WizardDataDTO> find(long id) {
        try {
            WizardDataDTO w = jdbcTemplate.queryForObject(
                    "SELECT * FROM cotizacion_seguro WHERE id = ?",
                    (ResultSet rs, int rowNum) -> {
                        WizardDataDTO dto = new WizardDataDTO();
                        dto.setNombre(rs.getString("nombre"));
                        dto.setEdad(rs.getInt("edad"));
                        dto.setAniosCarnet(rs.getInt("anios_carnet"));
                        dto.setMarca(rs.getString("marca"));
                        dto.setModelo(rs.getString("modelo"));
                        dto.setAnioMat(rs.getInt("anio_mat"));
                        dto.setUso(rs.getString("uso"));
                        dto.setTipoCobertura(rs.getString("tipo_cobertura"));
                        dto.setAsistencia(rs.getBoolean("asistencia"));
                        dto.setVehSustitucion(rs.getBoolean("veh_sustitucion"));
                        dto.setPrecioTotal(rs.getBigDecimal("precio_total"));
                        return dto;
                    },
                    id
            );
            return Optional.of(w);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(WizardDataDTO w) {
        int rowsUpdated = jdbcTemplate.update(
                """
                UPDATE cotizacion_seguro SET
                    nombre = ?, edad = ?, anios_carnet = ?, marca = ?, modelo = ?,
                    anio_mat = ?, uso = ?, tipo_cobertura = ?, asistencia = ?, veh_sustitucion = ?, precio_total = ?
                WHERE id = ?
                """,
                w.getNombre(),
                w.getEdad(),
                w.getAniosCarnet(),
                w.getMarca(),
                w.getModelo(),
                w.getAnioMat(),
                w.getUso(),
                w.getTipoCobertura(),
                w.isAsistencia(),
                w.isVehSustitucion(),
                w.getPrecioTotal()
                // Si quieres usar id, añade un campo id en DTO
                // , w.getId()
        );
        log.debug("Update de Cotizacion con {} registros actualizados.", rowsUpdated);
    }

    @Override
    public void delete(long id) {
        int rowsDeleted = jdbcTemplate.update(
                "DELETE FROM cotizacion_seguro WHERE id = ?",
                id
        );
        log.debug("Delete de Cotizacion con {} registros eliminados.", rowsDeleted);
    }
}