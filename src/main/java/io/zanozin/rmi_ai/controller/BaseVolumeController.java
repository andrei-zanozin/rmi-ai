package io.zanozin.rmi_ai.controller;

import io.zanozin.rmi_ai.domain.dto.BaseVolumeDto;
import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import io.zanozin.rmi_ai.repository.BaseVolumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@RestController
@RequestMapping("/base-volume")
@RequiredArgsConstructor
public class BaseVolumeController {

    private final BaseVolumeRepository baseVolumeRepository;

    @PostMapping
    @Transactional(REQUIRES_NEW)
    public void createBaseVolume(@RequestBody BaseVolumeDto bvDto) {
        BaseVolume bv = new BaseVolume();
        bv.setCategory(bvDto.getCategory());
        bv.setSupplier(bvDto.getSupplier());
        bv.setPlant(bvDto.getPlant());
        bv.setPart(bvDto.getPart());
        bv.setValue(bvDto.getValue());
        baseVolumeRepository.save(bv);
    }

    @GetMapping("/{id}")
    public BaseVolumeDto getBaseVolume(@PathVariable Long id) {
        BaseVolume bv = baseVolumeRepository.findById(id)
                .orElseThrow();
        BaseVolumeDto bvDto = new BaseVolumeDto();
        bvDto.setId(bv.getId());
        bvDto.setCategory(bv.getCategory());
        bvDto.setSupplier(bv.getSupplier());
        bvDto.setPlant(bv.getPlant());
        bvDto.setPart(bv.getPart());
        bvDto.setValue(bv.getValue());

        return bvDto;
    }

    @DeleteMapping("/{id}")
    @Transactional(REQUIRES_NEW)
    public void deleteBaseVolume(@PathVariable Long id) {
        baseVolumeRepository.deleteById(id);
    }
}
