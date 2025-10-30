package io.zanozin.rmi_ai;

import io.zanozin.rmi_ai.domain.Mapper;
import io.zanozin.rmi_ai.domain.dto.*;
import io.zanozin.rmi_ai.domain.entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void mapContainerReturnsDtoWithIdAndName() {
        Container container = new Container();
        container.setId(10L);
        container.setName("Container A");

        ContainerDto dto = Mapper.map(container);

        assertNotNull(dto);
        assertEquals(10L, dto.getId());
        assertEquals("Container A", dto.getName());
    }

    @Test
    void mapBaseVolumeReturnsDtoWithAllFields() {
        BaseVolume bv = new BaseVolume();
        bv.setId(20L);
        bv.setCategory("Cat");
        bv.setSupplier("Supp");
        bv.setPlant("PlantX");
        bv.setPart("PartY");
        bv.setValue(BigDecimal.valueOf(123.45));

        BaseVolumeDto dto = Mapper.map(bv);

        assertNotNull(dto);
        assertEquals(20L, dto.getId());
        assertEquals("Cat", dto.getCategory());
        assertEquals("Supp", dto.getSupplier());
        assertEquals("PlantX", dto.getPlant());
        assertEquals("PartY", dto.getPart());
        assertEquals(BigDecimal.valueOf(123.45), dto.getValue());
    }

    @Test
    void mapContainerRawMaterialAndOperatingWeightReturnBasicFields() {
        ContainerRawMaterial crm = new ContainerRawMaterial();
        crm.setId(30L);
        crm.setName("RawMat");

        ContainerOperatingWeight cow = new ContainerOperatingWeight();
        cow.setId(31L);
        cow.setValue(BigDecimal.valueOf(9.9));

        ContainerRawMaterialDto crmDto = Mapper.map(crm);
        ContainerOperatingWeightDto cowDto = Mapper.map(cow);

        assertNotNull(crmDto);
        assertEquals(30L, crmDto.getId());
        assertEquals("RawMat", crmDto.getName());

        assertNotNull(cowDto);
        assertEquals(31L, cowDto.getId());
        assertEquals(BigDecimal.valueOf(9.9), cowDto.getValue());
    }

    @Test
    void deepMapContainerMapsBaseVolumesAndRawMaterials() {
        BaseVolume bv = new BaseVolume();
        bv.setId(40L);
        bv.setCategory("BV Cat");

        ContainerOperatingWeight cow = new ContainerOperatingWeight();
        cow.setId(51L);
        cow.setValue(BigDecimal.valueOf(5.5));

        ContainerRawMaterial crm = new ContainerRawMaterial();
        crm.setId(50L);
        crm.setName("CRM1");
        crm.setOperatingWeights(List.of(cow));

        Container container = new Container();
        container.setId(41L);
        container.setName("Container Deep");
        container.setBaseVolumes(List.of(bv));
        container.setRawMaterials(List.of(crm));

        ContainerDto dto = Mapper.deepMap(container);

        assertNotNull(dto);
        assertEquals(41L, dto.getId());
        assertEquals("Container Deep", dto.getName());

        assertNotNull(dto.getBaseVolumes());
        assertEquals(1, dto.getBaseVolumes().size());
        assertEquals(40L, dto.getBaseVolumes().get(0).getId());
        assertEquals("BV Cat", dto.getBaseVolumes().get(0).getCategory());

        assertNotNull(dto.getRawMaterials());
        assertEquals(1, dto.getRawMaterials().size());
        ContainerRawMaterialDto crmDto = dto.getRawMaterials().get(0);
        assertEquals(50L, crmDto.getId());
        assertEquals("CRM1", crmDto.getName());
        assertNotNull(crmDto.getOperatingWeights());
        assertEquals(1, crmDto.getOperatingWeights().size());
        assertEquals(51L, crmDto.getOperatingWeights().get(0).getId());
    }

    @Test
    void deepMapContainerHandlesNullAndEmptyCollectionsGracefully() {
        Container cNull = new Container();
        cNull.setId(60L);
        cNull.setName("NullCollections");
        cNull.setBaseVolumes(null);
        cNull.setRawMaterials(null);

        ContainerDto dtoNull = Mapper.deepMap(cNull);
        assertNotNull(dtoNull);
        assertEquals(60L, dtoNull.getId());
        assertEquals("NullCollections", dtoNull.getName());
        assertNull(dtoNull.getBaseVolumes());
        assertNull(dtoNull.getRawMaterials());

        Container cEmpty = new Container();
        cEmpty.setId(61L);
        cEmpty.setName("EmptyCollections");
        cEmpty.setBaseVolumes(List.of());
        cEmpty.setRawMaterials(List.of());

        ContainerDto dtoEmpty = Mapper.deepMap(cEmpty);
        assertNotNull(dtoEmpty);
        assertEquals(61L, dtoEmpty.getId());
        assertEquals("EmptyCollections", dtoEmpty.getName());
        assertNull(dtoEmpty.getBaseVolumes());
        assertNull(dtoEmpty.getRawMaterials());
    }

    @Test
    void deepMapContainerOperatingWeightMapsKpiWhenPresent() {
        Kpi kpi = new Kpi();
        kpi.setId(70L);
        kpi.setRmiPercent(BigDecimal.valueOf(77.7));

        ContainerOperatingWeight cow = new ContainerOperatingWeight();
        cow.setId(71L);
        cow.setValue(BigDecimal.valueOf(1.1));
        cow.setKpi(kpi);

        ContainerOperatingWeightDto dto = Mapper.deepMap(cow);
        assertNotNull(dto);
        assertEquals(71L, dto.getId());
        assertEquals(BigDecimal.valueOf(1.1), dto.getValue());
        assertNotNull(dto.getKpi());
        assertEquals(70L, dto.getKpi().getId());
        assertEquals(BigDecimal.valueOf(77.7), dto.getKpi().getRmiPercent());
    }

    @Test
    void deepMapContainerRawMaterialMapsOperatingWeightsWhenAbsentOrEmpty() {
        ContainerRawMaterial crmNull = new ContainerRawMaterial();
        crmNull.setId(80L);
        crmNull.setName("CRM Null");
        crmNull.setOperatingWeights(null);

        ContainerRawMaterialDto dtoNull = Mapper.deepMap(crmNull);
        assertNotNull(dtoNull);
        assertEquals(80L, dtoNull.getId());
        assertNull(dtoNull.getOperatingWeights());

        ContainerRawMaterial crmEmpty = new ContainerRawMaterial();
        crmEmpty.setId(81L);
        crmEmpty.setName("CRM Empty");
        crmEmpty.setOperatingWeights(List.of());

        ContainerRawMaterialDto dtoEmpty = Mapper.deepMap(crmEmpty);
        assertNotNull(dtoEmpty);
        assertNull(dtoEmpty.getOperatingWeights());
    }
}
