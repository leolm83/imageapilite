package com.leolm.imageliteapi.domain.enums;

import org.springframework.http.MediaType;

import java.util.Arrays;

public enum ImageExtension {
    PNG(MediaType.IMAGE_PNG),
    JPEG(MediaType.IMAGE_JPEG),
    GIF(MediaType.IMAGE_GIF);

    private MediaType mediaType;

    ImageExtension(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static ImageExtension valueOf(MediaType mediaType){
        // TODO THROW UNSUPORTED MEDIA TYPE RUNTIMEEXCEPTION
        return Arrays.stream(values()).filter(item -> item.mediaType.equals(mediaType)).findFirst().orElse(null);
    }
}
