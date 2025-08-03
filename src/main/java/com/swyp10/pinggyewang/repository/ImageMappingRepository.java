package com.swyp10.pinggyewang.repository;

import com.swyp10.pinggyewang.domain.ImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageMappingRepository extends JpaRepository<ImageMapping, Long> {
    Optional<ImageMapping> findByTargetAndTone(String target, String tone);
}
