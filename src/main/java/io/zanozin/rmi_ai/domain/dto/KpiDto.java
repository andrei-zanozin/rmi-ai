package io.zanozin.rmi_ai.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KpiDto {

    private Long id;

    private BigDecimal rmiPercent;
}
