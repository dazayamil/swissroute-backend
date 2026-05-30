package com.swissroute.backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ExternalConnectionDTO {
    private ExternalStopDTO from;
    private ExternalStopDTO to;

    private String duration;
    private List<String> products;
    private List<SectionDTO> sections;
}
