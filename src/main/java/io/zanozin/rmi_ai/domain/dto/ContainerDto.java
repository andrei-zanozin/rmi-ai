package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ContainerDto {

    private Long id;

    private String name;

    private Set<Long> baseVolumeIds;

    private List<ContainerRawMaterialDto> rawMaterials;
}
