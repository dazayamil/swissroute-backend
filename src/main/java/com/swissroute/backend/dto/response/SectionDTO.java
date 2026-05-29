package com.swissroute.backend.dto.response;

import lombok.Data;

/*
 * Representa una sección del viaje.
 *
 * Por ejemplo:
 * un tren específico dentro de una conexión.
 */

@Data
public class SectionDTO {

    private JourneyDTO journey;
    
}

