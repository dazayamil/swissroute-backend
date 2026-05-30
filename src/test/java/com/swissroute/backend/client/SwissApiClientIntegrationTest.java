package com.swissroute.backend.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SwissApiClientIntegrationTest {
    private WireMockServer wireMockServer;

    @Autowired
    private SwissApiClient swissApiClient;

    @BeforeEach
    void setup(){
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8089));
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterEach
    void tearDown(){
        wireMockServer.stop();
    }

    @Test
    void givenValidQuery_whenGetLocations_thenReturn200(){
        wireMockServer.stubFor(get(urlPathEqualTo("/v1/locations"))
                .withQueryParam("query", equalTo("Basel"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                  "stations": [
                                    {
                                      "id": "8500010",
                                      "name": "Basel SBB"
                                    }
                                  ]
                                }
                                """)));
        Map<String, Object> response = swissApiClient.get(
                "/v1/locations?query=Base1",
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        assertThat(response).isNotNull();
        assertThat(response).containsKey("stations");

        List<?> stations = (List<?>) response.get("stations");
        assertThat(stations).hasSize(1);
    }
}
