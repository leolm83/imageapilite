package com.leolm.imageliteapi.application.images;


import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.domain.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
public class ImagesController {

    private ImageService imageService;

    public ImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity save(
        @RequestParam(value = "file",required = true)  MultipartFile file,
        @RequestParam("name") String name,
        @RequestParam("tags") List<String> tags
    ) throws IOException {
        log.info("IMAGEM RECEBIDA: NAME {},SIZE {}", file.getOriginalFilename(), file.getSize());
        log.info("NOME DEFINIDO PARA A IMAGEM: {}",name);
        log.info("TAGS: {}", tags);
        log.info("CONTENT TYPE {}",file.getContentType());
        log.info("MEDIA TYPE {}",MediaType.valueOf(file.getContentType()));
        Image image = new Image().builder()
                .name(name)
                .tags(String.join(",",tags))
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();
        imageService.save(image);
        return ResponseEntity.ok().build();
    }
}
