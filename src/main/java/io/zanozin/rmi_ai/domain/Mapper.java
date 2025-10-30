package io.zanozin.rmi_ai.domain;

import io.zanozin.rmi_ai.domain.dto.*;
import io.zanozin.rmi_ai.domain.entity.*;

public class Mapper {

    public static ContainerDto map(Container c) {
        ContainerDto cDto = new ContainerDto();
        cDto.setId(c.getId());
        cDto.setName(c.getName());

        return cDto;
    }

    public static BaseVolumeDto map(BaseVolume bv) {
        BaseVolumeDto bvDto = new BaseVolumeDto();
        bvDto.setId(bv.getId());
        bvDto.setCategory(bv.getCategory());
        bvDto.setSupplier(bv.getSupplier());
        bvDto.setPlant(bv.getPlant());
        bvDto.setPart(bv.getPart());
        bvDto.setValue(bv.getValue());

        return bvDto;
    }

    public static ContainerRawMaterialDto map(ContainerRawMaterial crm) {
        ContainerRawMaterialDto crmDto = new ContainerRawMaterialDto();
        crmDto.setId(crm.getId());
        crmDto.setName(crm.getName());

        return crmDto;
    }

    public static ContainerOperatingWeightDto map(ContainerOperatingWeight cow) {
        ContainerOperatingWeightDto cowDto = new ContainerOperatingWeightDto();
        cowDto.setId(cow.getId());
        cowDto.setValue(cow.getValue());

        return cowDto;
    }

    public static ContainerDto deepMap(Container c) {
        ContainerDto cDto = map(c);

        if (c.getBaseVolumes() != null && !c.getBaseVolumes().isEmpty()) {
            cDto.setBaseVolumes(c.getBaseVolumes().stream()
                    .map(Mapper::map)
                    .toList());
        }

        if (c.getRawMaterials() != null && !c.getRawMaterials().isEmpty()) {
            cDto.setRawMaterials(c.getRawMaterials().stream()
                    .map(Mapper::deepMap)
                    .toList());
        }

        return cDto;
    }

    public static ContainerRawMaterialDto deepMap(ContainerRawMaterial crm) {
        ContainerRawMaterialDto crmDto = map(crm);

        if (crm.getOperatingWeights() != null && !crm.getOperatingWeights().isEmpty()) {
            crmDto.setOperatingWeights(crm.getOperatingWeights().stream()
                    .map(Mapper::deepMap)
                    .toList());
        }

        return crmDto;
    }

    public static ContainerOperatingWeightDto deepMap(ContainerOperatingWeight cow) {
        ContainerOperatingWeightDto cowDto = map(cow);

        if (cow.getKpi() != null) {
            cowDto.setKpi(Mapper.map(cow.getKpi()));
        }

        return cowDto;
    }

    public static KpiDto map(Kpi k) {
        KpiDto kDto = new KpiDto();
        kDto.setId(k.getId());
        kDto.setRmiPercent(k.getRmiPercent());

        return kDto;
    }
}
