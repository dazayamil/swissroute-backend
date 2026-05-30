package com.swissroute.backend.client;

import com.swissroute.backend.exception.ExternalApiClientException;
import com.swissroute.backend.exception.ExternalApiServerException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.lang.reflect.ParameterizedType;

@Component
public class SwissApiClient {
    private final WebClient webClient;

    public SwissApiClient(WebClient swissTransportWebClient){
        this.webClient = swissTransportWebClient;
    }

    public <T> T get(String uri, ParameterizedTypeReference<T> responseType){
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(
                                new ExternalApiClientException("Error 4xx: " + response.statusCode())))
                .onStatus(status -> status.is5xxServerError(),
                        response -> Mono.error(
                                new ExternalApiServerException("Error 5xx: " + response.statusCode())))
                .bodyToMono(responseType)
                .block();
    }

}
