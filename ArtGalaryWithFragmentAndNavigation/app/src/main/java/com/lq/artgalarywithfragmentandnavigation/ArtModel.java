package com.lq.artgalarywithfragmentandnavigation;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class ArtModel implements Serializable {
    String type;
    String name;

    int id;

    byte[] image;

    ArtModel(String type, @Nullable String name,@Nullable int id,@Nullable byte[] image)
    {
        this.type = type;
        this.name = name;
        this.id = id;
        this.image = image;
    }


}
