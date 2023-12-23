package com.leolm.imageliteapi.infra.repository;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.infra.repository.specs.GenericsSpecs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.List;

import static com.leolm.imageliteapi.infra.repository.specs.ImageSpecs.*;
import static com.leolm.imageliteapi.util.DBUtil.containsText;
import static org.springframework.data.jpa.domain.Specification.anyOf;

public interface ImageRepository extends JpaRepository<Image, UUID> , JpaSpecificationExecutor<Image> {

    default  List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        System.out.println("Extension "+extension);
        Specification<Image> specification = Specification.where(GenericsSpecs.conjunction());
        if(extension != null){
            specification = specification.and(extensionEqual(extension));
        }
        if(StringUtils.hasText(query)){
            specification = specification.and(anyOf(nameLike(query),tagsLike(query)));
        }
        return findAll(specification);
    }
}
