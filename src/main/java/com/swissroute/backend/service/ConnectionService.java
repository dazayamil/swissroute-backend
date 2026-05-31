package com.swissroute.backend.service;

import com.swissroute.backend.client.SwissApiClient;
import com.swissroute.backend.dto.response.ConnectionDto;
import com.swissroute.backend.dto.response.ConnectionsResponseDTO;
import com.swissroute.backend.dto.response.ExternalConnectionDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionService {

    private final SwissApiClient swissApiClient;
    private final SearchHistoryService searchHistoryService;

    public ConnectionService(SwissApiClient swissApiClient, SearchHistoryService searchHistoryService){
        this.swissApiClient = swissApiClient;
        this.searchHistoryService = searchHistoryService;
    }

    public List<ConnectionDto> getConnections(
            String from,
            String to,
            String date,
            String time,
            String transportations,
            List<String> via){
        if(via != null && via.size() > 5){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A maximum of 5 intermediate stops (via) is allowed");
        }

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
        if(via != null && !via.isEmpty()){
            for(String stop : via){
                uri.append("&via[]=").append(stop);
            }
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

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        searchHistoryService.saveHistory(
                email,
                from,
                to,
                result.size()
        );

        return result;
    }
}
