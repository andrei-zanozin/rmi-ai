package io.zanozin.rmi_ai.service;

import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import io.zanozin.rmi_ai.repository.BaseVolumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseVolumeService {

    private final BaseVolumeRepository baseVolumeRepository;

    @Tool(description = "Find base volume IDs by base volume attributes")
    public Set<Long> findBaseVolumeIdsByAttributes(
            @ToolParam(description = "Value for attribute `category`", required = false) String category,
            @ToolParam(description = "Value for attribute `supplier`", required = false) String supplier,
            @ToolParam(description = "Value for attribute `plant`", required = false) String plant,
            @ToolParam(description = "Value for attribute `part`", required = false) String part
    ) {
        log.debug("Finding base volume IDs by attributes: category='{}', supplier='{}', plant='{}', part='{}'",
                category, supplier, plant, part);
        BaseVolume bv = new BaseVolume();
        bv.setCategory(category);
        bv.setSupplier(supplier);
        bv.setPlant(plant);
        bv.setPart(part);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues();
        Example<BaseVolume> bvExample = Example.of(bv, exampleMatcher);
        Set<Long> bvIds = baseVolumeRepository.findAll(bvExample).stream()
                .map(BaseVolume::getId)
                .collect(Collectors.toSet());
        log.debug("Found base volume IDs by attributes category='{}', supplier='{}', plant='{}', part='{}': {}",
                category, supplier, plant, part, bvIds);

        return bvIds;
    }
}
