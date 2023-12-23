package com.leolm.imageliteapi.domain.service;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {

    Image save(Image image);

    Optional<Image> findById(UUID id);

    List<Image> search(ImageExtension extension, String query);
}
