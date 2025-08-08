package com.swyp10.pinggyewang.service;

import com.swyp10.pinggyewang.domain.ImageMapping;
import com.swyp10.pinggyewang.domain.Target;
import com.swyp10.pinggyewang.repository.ImageMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageMappingService {

    private final ImageMappingRepository mappingRepo;

    public String selectImageKey(Target target, String tone){
        return mappingRepo.findByTargetAndTone(target, tone.trim())
                .map(ImageMapping::getImageKey)
                .orElse("B") // 기본 이미지
                ;
    }
}
