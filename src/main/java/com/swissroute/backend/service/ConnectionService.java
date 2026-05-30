package com.swissroute.backend.service;

import com.swissroute.backend.client.SwissApiClient;
import com.swissroute.backend.dto.response.ConnectionDto;
import com.swissroute.backend.dto.response.ConnectionsResponseDTO;
import com.swissroute.backend.dto.response.ExternalConnectionDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {

    private final SwissApiClient swissApiClient;

    public ConnectionService(SwissApiClient swissApiClient){
        this.swissApiClient = swissApiClient;
    }

    public List<ConnectionDto> getConnections(
            String from,
            String to,
            String date,
            String time,
            String transportations){

        StringBuilder uri = new StringBuilder(
                "/connections?from=" + from + "&to=" + to
        );

        if(date != null && !date.isBlank()){
            uri.append("&date=").append(date);
        }

        if(time != null && !time.isBlank()){
            uri.append("&time=").append(time);
        }

        if(transportations != null && !transportations.isBlank()){
            uri.append("&transportations=").append(transportations);
        }

        ConnectionsResponseDTO response =
                swissApiClient.get(
                        uri.toString(),
                        new ParameterizedTypeReference<ConnectionsResponseDTO>() {
                        }
                );

        List<ConnectionDto> result = new ArrayList<>();
        for(ExternalConnectionDTO external : response.getConnections()){

            ConnectionDto dto = new ConnectionDto();
            dto.setOrigin(external.getFrom().getStation().getName());
            dto.setDestination(external.getTo().getStation().getName());
            dto.setDuration(external.getDuration());
            dto.setProducts(external.getProducts());
            dto.setSections(external.getSections());

            result.add(dto);
        }

        return result;
    }
}
