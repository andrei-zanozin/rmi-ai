package io.zanozin.rmi_ai.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class BaseVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private Container container;

    private String category;

    private String supplier;

    private String plant;

    private String part;

    /**
     * Manufacturing volume, EUR.
     */
    private BigDecimal value;
}
