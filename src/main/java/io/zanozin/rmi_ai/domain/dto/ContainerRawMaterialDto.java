package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContainerRawMaterialDto {

    private Long id;

    private String name;

    private List<ContainerOperatingWeightDto> operatingWeights;
}
