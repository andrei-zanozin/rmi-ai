package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseVolumeDto {

    private Long id;

    private String category;

    private String supplier;

    private String plant;

    private String part;

    private BigDecimal value;
}
