package com.swissroute.backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ExternalStationboardResponseDTO {
    private List<ExternalStationboardEntryDTO> stationboard;
}
