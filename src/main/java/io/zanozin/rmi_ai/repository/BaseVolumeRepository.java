package io.zanozin.rmi_ai.repository;

import io.zanozin.rmi_ai.domain.entity.BaseVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseVolumeRepository extends JpaRepository<BaseVolume, Long> {
}
