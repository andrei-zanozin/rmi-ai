package io.zanozin.rmi_ai;

import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import io.zanozin.rmi_ai.domain.entity.Container;
import io.zanozin.rmi_ai.repository.BaseVolumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContainerService {

    private final BaseVolumeRepository baseVolumeRepository;

    @Tool(description = "Find container names by base volume parameters")
    @Transactional
    public Set<String> findContainerNamesByBaseVolumeParameters(
            @ToolParam(description = "Value for base volume parameter `category`", required = false) String category,
            @ToolParam(description = "Value for base volume parameter `supplier`", required = false) String supplier,
            @ToolParam(description = "Value for base volume parameter `plant`", required = false) String plant,
            @ToolParam(description = "Value for base volume parameter `part`", required = false) String part
    ) {
        log.debug("Finding container names by base volume parameters: category={}, supplier={}, plant={}, part={}",
                category, supplier, plant, part);
        BaseVolume bv = BaseVolume.builder()
                .category(category)
                .supplier(supplier)
                .plant(plant)
                .part(part)
                .build();
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues();
        Example<BaseVolume> bvExample = Example.of(bv, exampleMatcher);
        Set<String> containerNames = baseVolumeRepository.findAll(bvExample).stream()
                .map(BaseVolume::getContainer)
                .filter(Objects::nonNull)
                .map(Container::getName)
                .collect(Collectors.toSet());
        log.debug("Found container names: {}", containerNames);

        return containerNames;
    }
}
