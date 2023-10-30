package com.mokpo.capstonedesign.retrofit2;

public class Recipe {
    private String RCP_NM;
    private String image_link;

    // getter methods
    public String getRCP_NM() {
        return RCP_NM;
    }

    public String getImage_link() {
        return image_link;
    }

    // setter methods
    public void setRCP_NM(String RCP_NM) {
        this.RCP_NM = RCP_NM;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}

