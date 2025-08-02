package com.swyp10.pinggyewang.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "image_mapping", uniqueConstraints = {
        @UniqueConstraint(name = "uk_map", columnNames = {"situation", "tone"})
})
public class ImageMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String tone;

    @Column(name = "image_key", nullable = false)
    private String imageKey;

    public Long getId() {
        return id;
    }

    public String getTone() {
        return tone;
    }

    public String getTarget() {
        return target;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
