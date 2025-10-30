package io.zanozin.rmi_ai.repository;

import io.zanozin.rmi_ai.domain.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {

    boolean existsByName(String name);

    Optional<Container> findByName(String name);
}
