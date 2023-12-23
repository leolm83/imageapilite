package com.leolm.imageliteapi.infra.repository.specs;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.util.DBUtil;
import org.springframework.data.jpa.domain.Specification;


public class ImageSpecs {

    private ImageSpecs(){}

    public static Specification<Image> extensionEqual(ImageExtension extension){
        return  (root, q, cb) -> cb.equal(root.get("extension"),extension);
    }

    public static Specification<Image> nameLike(String name){
        return  (root, q, cb) -> cb.equal(root.get("name"), DBUtil.containsText(name));
    }

    public static Specification<Image> tagsLike(String tag){
        return  (root, q, cb) -> cb.equal(root.get("tags"), DBUtil.containsText(tag));
    }
}
