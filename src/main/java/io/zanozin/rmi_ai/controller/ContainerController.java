package io.zanozin.rmi_ai.controller;

import io.zanozin.rmi_ai.domain.dto.ContainerOperatingWeightDto;
import io.zanozin.rmi_ai.domain.dto.ContainerRawMaterialDto;
import io.zanozin.rmi_ai.domain.dto.KpiDto;
import io.zanozin.rmi_ai.domain.entity.*;
import io.zanozin.rmi_ai.repository.BaseVolumeRepository;
import io.zanozin.rmi_ai.repository.ContainerRepository;
import io.zanozin.rmi_ai.domain.dto.ContainerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@RestController
@RequestMapping("/container")
@RequiredArgsConstructor
public class ContainerController {

    private final ContainerRepository containerRepository;

    private final BaseVolumeRepository baseVolumeRepository;

    @PostMapping
    @Transactional(REQUIRES_NEW)
    public void createContainer(@RequestBody ContainerDto cDto) {
        Container c = new Container();
        c.setName(cDto.getName());
        c.setBaseVolumes(baseVolumeRepository.findAllById(cDto.getBaseVolumeIds()));
        List<ContainerRawMaterial> crmList = cDto.getRawMaterials().stream()
                .map(crmDto -> buildContainerRawMaterial(c, crmDto))
                .toList();
        c.setRawMaterials(crmList);

        containerRepository.save(c);
    }

    @GetMapping
    public ContainerDto getContainer(@RequestParam Long id) {
        Container c = containerRepository.findById(id)
                .orElseThrow();
        ContainerDto cDto = new ContainerDto();
        cDto.setId(c.getId());
        cDto.setName(c.getName());
        cDto.setBaseVolumeIds(c.getBaseVolumes().stream()
                .map(BaseVolume::getId)
                .collect(Collectors.toSet()));
        List<ContainerRawMaterialDto> crmDtoList = c.getRawMaterials().stream()
                .map(crm -> {
                    ContainerRawMaterialDto crmDto = new ContainerRawMaterialDto();
                    crmDto.setId(crm.getId());
                    crmDto.setName(crm.getName());
                    List<ContainerOperatingWeightDto> cowDtoList = crm.getOperatingWeights().stream()
                            .map(cow -> {
                                ContainerOperatingWeightDto cowDto = new ContainerOperatingWeightDto();
                                cowDto.setBaseVolumeId(cow.getBaseVolume().getId());
                                cowDto.setValue(cow.getValue());
                                Kpi kpi = cow.getKpi();

                                if (kpi != null) {
                                    KpiDto kpiDto = new KpiDto();
                                    kpiDto.setId(kpi.getId());
                                    kpiDto.setRmiPercent(kpi.getRmiPercent());
                                    cowDto.setKpi(kpiDto);
                                }

                                return cowDto;
                            })
                            .toList();
                    crmDto.setOperatingWeights(cowDtoList);
                    return crmDto;
                })
                .toList();
        cDto.setRawMaterials(crmDtoList);

        return cDto;
    }

    @DeleteMapping("/{id}")
    @Transactional(REQUIRES_NEW)
    public void deleteContainer(@PathVariable Long id) {
        containerRepository.deleteById(id);
    }

    private ContainerRawMaterial buildContainerRawMaterial(Container c, ContainerRawMaterialDto crmDto) {
        ContainerRawMaterial crm = new ContainerRawMaterial();
        crm.setName(crmDto.getName());
        List<ContainerOperatingWeight> cowList = crmDto.getOperatingWeights().stream()
                .map(cowDto -> buildContainerOperatingWeight(crm, cowDto))
                .toList();
        crm.setOperatingWeights(cowList);
        crm.setContainer(c);

        if (c.getRawMaterials() == null) {
            c.setRawMaterials(new ArrayList<>());
        }

        c.getRawMaterials().add(crm);

        return crm;
    }

    private ContainerOperatingWeight buildContainerOperatingWeight(ContainerRawMaterial crm, ContainerOperatingWeightDto cowDto) {
        ContainerOperatingWeight cow = new ContainerOperatingWeight();
        BaseVolume bv = baseVolumeRepository.findById(cowDto.getBaseVolumeId())
                .orElseThrow();
        cow.setBaseVolume(bv);
        cow.setValue(cowDto.getValue());
        cow.setContainerRawMaterial(crm);

        if (crm.getOperatingWeights() == null) {
            crm.setOperatingWeights(new ArrayList<>());
        }

        crm.getOperatingWeights().add(cow);

        return cow;
    }
}
