package io.zanozin.rmi_ai.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_operating_weight_id")
    private ContainerOperatingWeight containerOperatingWeight;

    /**
     * Raw Material Impact, %.
     */
    private BigDecimal rmiPercent;
}
