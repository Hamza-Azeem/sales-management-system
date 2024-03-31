package com.project.salesmanagementsystem.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ClientReportDTO {
    private int totalClients;
    private ClientDTO topClient;
    private List<LocationStatDTO> locationStatDTOS;

}
