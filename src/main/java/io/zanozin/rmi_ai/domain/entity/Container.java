package io.zanozin.rmi_ai.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Container {

    @Id
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "container",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<BaseVolume> baseVolumes;

    @OneToMany(
            mappedBy = "container",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<ContainerRawMaterial> rawMaterials;
}
