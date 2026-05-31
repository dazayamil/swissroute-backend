package com.swissroute.backend.service;

import com.swissroute.backend.client.SwissApiClient;
import com.swissroute.backend.dto.response.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {

    @Mock
    private SwissApiClient swissApiClient;

    @InjectMocks
    private ConnectionService connectionService;

    /*
     * Verifica que el servicio devuelva
     * correctamente una conexión obtenida
     * desde la API externa.
     */
    @Test
    void getConnections_success(){

        ConnectionsResponseDTO response = new ConnectionsResponseDTO();

        ExternalConnectionDTO externalConnection = new ExternalConnectionDTO();

        externalConnection.setDuration("00d00:43:00");

        ExternalStationDTO fromStation = new ExternalStationDTO();
        fromStation.setName("Lausanne");

        ExternalStopDTO from = new ExternalStopDTO();
        from.setStation(fromStation);

        externalConnection.setFrom(from);

        ExternalStationDTO toStation = new ExternalStationDTO();
        toStation.setName("Genève");

        ExternalStopDTO to = new ExternalStopDTO();
        to.setStation(toStation);

        externalConnection.setTo(to);

        response.setConnections(List.of(externalConnection));

        when(swissApiClient.get(anyString(), any()))
                .thenReturn(response);

        List<ConnectionDto> result =
                connectionService.getConnections(
                        "Lausanne",
                        "Genève",
                        null,
                        null,
                        null,
                        null
                );

        assertEquals(1, result.size());

        assertEquals("Lausanne", result.get(0).getOrigin());

        assertEquals("Genève", result.get(0).getDestination());

        assertEquals("00d00:43:00", result.get(0).getDuration());

    }

    /*
     * Verifica que el servicio devuelva
     * una lista vacía cuando la API externa
     * no encuentre conexiones.
     */
    @Test
    void getConnections_emptyResponse() {

        ConnectionsResponseDTO response = new ConnectionsResponseDTO();
        response.setConnections(List.of());

        when(swissApiClient.get(anyString(), any()))
                .thenReturn(response);

        List<ConnectionDto> result =
                connectionService.getConnections(
                        "Lausanne",
                        "Genève",
                        null,
                        null,
                        null,
                        null
                );

        assertTrue(result.isEmpty());
    }

    /*
     * Verifica que el mapeo desde la API externa
     * hacia ConnectionDto se realice correctamente.
     */
    @Test
    void getConnections_mapsDataCorrectly() {

        // Arrange
        ConnectionsResponseDTO response = new ConnectionsResponseDTO();

        ExternalConnectionDTO externalConnection = new ExternalConnectionDTO();

        ExternalStationDTO fromStation = new ExternalStationDTO();
        fromStation.setName("Lausanne");

        ExternalStopDTO from = new ExternalStopDTO();
        from.setStation(fromStation);

        ExternalStationDTO toStation = new ExternalStationDTO();
        toStation.setName("Genève");

        ExternalStopDTO to = new ExternalStopDTO();
        to.setStation(toStation);

        externalConnection.setFrom(from);
        externalConnection.setTo(to);
        externalConnection.setDuration("00d00:43:00");
        externalConnection.setProducts(List.of("IC 1"));

        response.setConnections(List.of(externalConnection));

        when(swissApiClient.get(anyString(), any()))
                .thenReturn(response);

        // Act
        List<ConnectionDto> result =
                connectionService.getConnections(
                        "Lausanne",
                        "Genève",
                        null,
                        null,
                        null,
                        null
                );

        ConnectionDto connection = result.get(0);

        assertEquals("Lausanne", connection.getOrigin());
        assertEquals("Genève", connection.getDestination());
        assertEquals("00d00:43:00", connection.getDuration());
        assertEquals(List.of("IC 1"), connection.getProducts());
    }
}
