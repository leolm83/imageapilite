package com.leolm.imageliteapi.domain.entity;

import com.leolm.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Long size;
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;

    @CreatedDate
    private LocalDateTime uploadDate;

    private String tags;
    @Lob
    private byte[] file;

    public String getFileFullName(){
        return getName().concat(".").concat(getExtension().name());
    }


}
