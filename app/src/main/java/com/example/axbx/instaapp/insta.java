package com.example.axbx.instaapp;

/**
 * Created by axbx on 16/01/18.
 */

public class insta {
    private String title,desc,image;
    public  insta(){

    }

    public insta(String title,String desc,String image){

        this.title=title;
        this.desc=desc;
        this.image=image;


    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setDesc(String desc) {

        this.desc=desc;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
