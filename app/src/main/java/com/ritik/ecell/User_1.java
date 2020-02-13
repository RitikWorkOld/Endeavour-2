package com.ritik.ecell;

public class User_1 {
    public String name;
    public int imageId;


    public User_1(){}

    public User_1(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }


    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
