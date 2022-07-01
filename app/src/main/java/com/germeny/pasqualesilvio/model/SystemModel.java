package com.germeny.pasqualesilvio.model;

public class SystemModel {
    private int image;
    private String name;

    public SystemModel(String name, int image){
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
