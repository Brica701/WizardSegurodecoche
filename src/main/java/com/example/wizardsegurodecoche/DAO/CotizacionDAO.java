package com.example.wizardsegurodecoche.DAO;

import com.example.wizardsegurodecoche.DTO.WizardDataDTO;

import java.util.List;
import java.util.Optional;

public interface CotizacionDAO {

    void create(WizardDataDTO wizardData);

    List<WizardDataDTO> getAll();

    Optional<WizardDataDTO> find(long id);

    void update(WizardDataDTO wizardData);

    void delete(long id);
}