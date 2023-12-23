package com.leolm.imageliteapi.application.images;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.domain.service.ImageService;
import com.leolm.imageliteapi.infra.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;
    @Override
    @Transactional
    public Image save(Image image){
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findById(UUID id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        return imageRepository.findByExtensionAndNameOrTagsLike(extension,query);
    }


}
