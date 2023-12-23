package com.leolm.imageliteapi.application.images;


import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.domain.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Image image = ImageMapper.mapToImage(file,name,tags);
        Image savedImage = imageService.save(image);
        URI imageURI = buildImageURL(savedImage);

        return ResponseEntity.created(imageURI).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") UUID id){
        Optional<Image> possibleImage = imageService.findById(id);
        if(possibleImage.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        Image image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        log.info("CONTENT DISPOSITION {}",ContentDisposition.inline().filename(image.getFileFullName()).build());
        ContentDisposition contentDisposition = ContentDisposition.inline().filename(image.getFileFullName()).build();
        headers.setContentDisposition(contentDisposition);
        return new ResponseEntity<>(image.getFile(),headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(
            @RequestParam(value = "extension", required = false) String extension,
            @RequestParam(value = "query", required = false) String query){

        log.info("EXTENSÃO {} | QUERY {}",extension,query);
        List<Image> result = imageService.search(ImageExtension.valueOfOrNull(extension), query);
        List<ImageDTO> images = result.stream().map(image -> {

            return  ImageMapper.imageToDTO(image, buildImageURL(image).toString());

        }).collect(Collectors.toList());
        return  ResponseEntity.ok(images);
    }


    @GetMapping("download/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("id") UUID id){
        Optional<Image> possibleImage = imageService.findById(id);
        if(possibleImage.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        Image image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());
        log.info("CONTENT DISPOSITION {}",ContentDisposition.formData().filename(image.getFileFullName()).build());
        ContentDisposition contentDisposition = ContentDisposition.formData().filename(image.getFileFullName()).build();
        headers.setContentDisposition(contentDisposition);
        return new ResponseEntity<>(image.getFile(),headers, HttpStatus.OK);
    }

    private URI buildImageURL(Image image){
        String imagePath = "/"+image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }
}
