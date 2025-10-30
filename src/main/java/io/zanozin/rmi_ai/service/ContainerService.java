package io.zanozin.rmi_ai.service;

import io.zanozin.rmi_ai.domain.Mapper;
import io.zanozin.rmi_ai.domain.dto.ContainerDto;
import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import io.zanozin.rmi_ai.domain.entity.Container;
import io.zanozin.rmi_ai.repository.BaseVolumeRepository;
import io.zanozin.rmi_ai.repository.ContainerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContainerService {

    private final BaseVolumeRepository baseVolumeRepository;

    private final ContainerRepository containerRepository;

    @Tool(description = "Find container names by base volume IDs")
    @Transactional
    public Set<String> findContainerNamesByBaseVolumeIds(
            @ToolParam(description = "Base volume ID values", required = false) Set<Long> baseVolumeIds
    ) {
        log.debug("Finding container names by base volume IDs: {}", baseVolumeIds);
        Set<String> cNames = baseVolumeRepository.findAllById(baseVolumeIds).stream()
                .map(BaseVolume::getContainer)
                .filter(Objects::nonNull)
                .map(Container::getName)
                .collect(Collectors.toSet());
        log.debug("Found container names by base volume IDs {}: {}", baseVolumeIds, cNames);

        return cNames;
    }

    @Tool(description = "Create a new empty container with the specified name")
    @Transactional
    public void createContainer(@ToolParam(description = "Value for the specified name") String name) {
        if (containerRepository.existsByName(name)) {
            throw new ContainerException("Container with name '%s' already exists".formatted(name));
        }

        Container c = new Container();
        c.setName(name);
        containerRepository.save(c);
    }

    @Tool(description = "Delete a container by the specified name")
    @Transactional
    public void deleteContainer(@ToolParam(description = "Value for the specified name") String name) {
        Optional<Container> cOpt = containerRepository.findByName(name);

        if (cOpt.isEmpty()) {
            throw new ContainerException("Container with name '%s' does not exist".formatted(name));
        } else {
            containerRepository.delete(cOpt.get());
        }
    }

    @Tool(description = "Find container 'id' by 'name'")
    public Long findContainerIdByName(@ToolParam(description = "Value of container 'name'") String name) {
        log.debug("Finding container id by name: {}", name);
        Optional<Container> cOpt = containerRepository.findByName(name);

        if (cOpt.isEmpty()) {
            throw new ContainerException("Container with name '%s' does not exist".formatted(name));
        } else {
            Container c = cOpt.get();
            log.debug("Found container id: {}", c.getId());

            return c.getId();
        }
    }

    @Tool(description = "Retrieve a container by 'id'")
    @Transactional
    public ContainerDto findContainerById(@ToolParam(description = "Value of container 'id'") Long id) {
        log.debug("Retrieving container by id: {}", id);
        Optional<Container> cOpt = containerRepository.findById(id);

        if (cOpt.isEmpty()) {
            throw new ContainerException("Container with id '%s' does not exist".formatted(id));
        } else {
            Container c = cOpt.get();
            ContainerDto cDto = Mapper.deepMap(c);
            log.debug("Retrieved container DTO: {}", cDto);

            return cDto;
        }
    }

    @Tool(description = "Add base volumes to a container")
    @Transactional
    public void addBaseVolumeToContainer(
            @ToolParam(description = "Value of container 'id'") Long containerId,
            @ToolParam(description = "Values of base volume 'id'") Set<Long> baseVolumeIds
    ) {
        log.debug("Adding base volumes {} to container {}", baseVolumeIds, containerId);
        Container c = containerRepository.findById(containerId)
                .orElseThrow(() -> new ContainerException("Container with id '%s' does not exist"
                        .formatted(containerId)));
        baseVolumeRepository.findAllById(baseVolumeIds).forEach(bv -> bv.setContainer(c));
        log.debug("Added base volumes {} to container {}", baseVolumeIds, containerId);
    }

    @Tool(description = "Remove base volumes from a container")
    @Transactional
    public void removeBaseVolumeFromContainer(
            @ToolParam(description = "Value of container 'id'") Long containerId,
            @ToolParam(description = "Values of base volume 'id'") Set<Long> baseVolumeIds
    ) {
        log.debug("Removing base volumes {} from container {}", baseVolumeIds, containerId);
        Set<Long> removedBaseVolumeIds = new HashSet<>();
        baseVolumeRepository.findAllById(baseVolumeIds).forEach(bv -> {
            if (bv.getContainer() != null && bv.getContainer().getId().equals(containerId)) {
                bv.setContainer(null);
                removedBaseVolumeIds.add(bv.getId());
            }
        });
        log.debug("Removed base volumes {} from container {}", removedBaseVolumeIds, containerId);
    }
}
