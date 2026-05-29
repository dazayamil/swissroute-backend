package com.swissroute.backend.dto.response;
/*
 * DTO que devolverá nuestro endpoint.
 *
 * Representa una conexión entre dos estaciones.
 */

import lombok.Data;

import java.util.List;

@Data
public class ConnectionDto {

    private String origin;
    private String destination;
    private String duration;
    private List<String> products;
    private List<SectionDTO> sections;

}
