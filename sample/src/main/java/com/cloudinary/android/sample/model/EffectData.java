package com.cloudinary.android.sample.model;

import com.cloudinary.Transformation;

public class EffectData {
    private final String thumbUrl;
    private final String imageUrl;
    private final String cloudinaryPublicId;
    private final String name;
    private final String description;
    private final Transformation transformation;

    public EffectData(String thumbUrl, String imageUrl, String name, String description, Transformation transformation , String cloudinaryPublicId) {
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.transformation = transformation;
        this.cloudinaryPublicId = cloudinaryPublicId;
    }

    public EffectData(String thumbUrl, String imageUrl, String name, String description) {
        this.thumbUrl = thumbUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.cloudinaryPublicId = "" ;
        this.transformation = null;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPublicId() {
        return cloudinaryPublicId;
    }


    public Transformation getTransformation() {
        return transformation;
    }


}
