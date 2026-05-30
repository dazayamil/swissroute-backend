package com.swissroute.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/*
 * Información del transporte utilizado
 * dentro de una sección.
 */
@Data
public class JourneyDTO {
    private String category;
    private String number;
    private String operator;

    @JsonAlias("to")
    private String destination;

}
