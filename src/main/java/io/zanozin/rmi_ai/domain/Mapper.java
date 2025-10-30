package io.zanozin.rmi_ai.domain;

import io.zanozin.rmi_ai.domain.dto.BaseVolumeDto;
import io.zanozin.rmi_ai.domain.dto.ContainerDto;
import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import io.zanozin.rmi_ai.domain.entity.Container;

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
}
