package io.zanozin.rmi_ai.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ContainerRawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id")
    private Container container;

    private String name;

    @OneToMany(
            mappedBy = "containerRawMaterial",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ContainerOperatingWeight> operatingWeights;
}
