package com.leolm.imageliteapi.application.images;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper {
    public static Image mapToImage(MultipartFile multipartFile, String name, List<String> tags) throws IOException {
        Image image = new Image().builder()
                .name(name)
                .tags(String.join(",",tags))
                .size(multipartFile.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(multipartFile.getContentType())))
                .file(multipartFile.getBytes())
                .build();

        return image;
    }
    public static ImageDTO imageToDTO(Image image, String url){
        return  ImageDTO.builder().url(url).extension(image.getExtension().name())
                .name(image.getName()).size(image.getSize()).uploadDate(image.getUploadDate().toLocalDate()).build();
    }

}
