package io.zanozin.rmi_ai.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ContainerOperatingWeight {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_raw_material_id")
    private ContainerRawMaterial containerRawMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_volume_id")
    private BaseVolume baseVolume;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "containerOperatingWeight",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Kpi kpi;

    /**
     * Operating weight value, kg.
     */
    private BigDecimal value;
}
