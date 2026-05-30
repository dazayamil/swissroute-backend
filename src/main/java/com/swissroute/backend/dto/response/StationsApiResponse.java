package com.swissroute.backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StationsApiResponse {
    private List<StationEntry> stations;

    @Data
    public static class StationEntry {
        private String id;
        private String name;
        private Coordinate coordinate;
        private Integer distance;

        @Data
        public static class Coordinate {
            private Double x;
            private Double y;
        }
    }
}
