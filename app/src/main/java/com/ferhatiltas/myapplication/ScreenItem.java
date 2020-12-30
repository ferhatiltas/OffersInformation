package com.ferhatiltas.myapplication;

public class ScreenItem {

  private   String title,description;
   private int screenImg;

    public ScreenItem(String title, String description, int screenImg) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
    }

    public String getTitle() {
        return title;
    }



    public String getDescription() {
        return description;
    }



    public int getScreenImg() {
        return screenImg;
    }


}
