package com.swissroute.backend.dto.response;

import lombok.Data;

@Data
public class ExternalStationboardEntryDTO {
    private String name;
    private String category;
    private String to;
    private ExternalStopInfoDTO stop;
}
