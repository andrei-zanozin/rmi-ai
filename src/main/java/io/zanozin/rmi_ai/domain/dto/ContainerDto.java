package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContainerDto {

    private Long id;

    private String name;

    private List<BaseVolumeDto> baseVolumes;

    private List<ContainerRawMaterialDto> rawMaterials;
}
