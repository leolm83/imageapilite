package com.leolm.imageliteapi.domain.enums;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.Arrays;

@Slf4j
public enum ImageExtension {

    JPEG(MediaType.IMAGE_JPEG),
    PNG(MediaType.IMAGE_PNG),

    GIF(MediaType.IMAGE_GIF);

    @Getter
    private MediaType mediaType;

    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static ImageExtension valueOf(MediaType mediaType){
        // TODO THROW UNSUPORTED MEDIA TYPE RUNTIMEEXCEPTION
        return Arrays.stream(values()).filter(item -> item.mediaType.equals(mediaType)).findFirst().orElse(null);
    }

    public static ImageExtension valueOfOrNull(String s){


            return Arrays.stream(values())
                    .filter(ie -> {
                        log.info("NAME {}",ie.name());
                        return ie.name().equalsIgnoreCase(s);
                    })
                    .findFirst()
                    .orElse(null);
        }
}
