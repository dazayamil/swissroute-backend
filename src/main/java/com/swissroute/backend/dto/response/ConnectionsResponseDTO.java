package com.swissroute.backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ConnectionsResponseDTO {
    private List<ExternalConnectionDTO> connections;
}
