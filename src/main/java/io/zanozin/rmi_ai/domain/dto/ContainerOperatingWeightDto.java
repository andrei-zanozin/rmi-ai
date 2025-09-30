package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContainerOperatingWeightDto {

    private Long id;

    private Long baseVolumeId;

    private BigDecimal value;

    private KpiDto kpi;
}
