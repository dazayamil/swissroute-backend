package com.swissroute.backend.controller;

import com.swissroute.backend.dto.response.ConnectionDto;
import com.swissroute.backend.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/connections")
@Tag(name = "Connections", description = "Transport connections from the Swiss public transport API")
public class ConnectionController {

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService){
        this.connectionService = connectionService;
    }

    @Operation(summary = "Search connections between two stations")
    @GetMapping
    public ResponseEntity<List<ConnectionDto>> getConnections(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String transportations,
            @RequestParam(value = "via[]", required = false) List<String> via){

        List<ConnectionDto> connections =
                connectionService.getConnections(
                        from,
                        to,
                        date,
                        time,
                        transportations,
                        via);

        return ResponseEntity.ok(connections);

    }
}
