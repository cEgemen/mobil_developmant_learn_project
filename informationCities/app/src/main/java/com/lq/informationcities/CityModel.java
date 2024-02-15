package com.lq.informationcities;

public class CityModel {
    private String name;
    private String info;
    private int image;

    CityModel(String name,String info,int image)
    {
        this.name=name;
        this.info=info;
        this.image=image;
    }

    public int getImage() {
        return image;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }
}
