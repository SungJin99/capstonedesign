package com.mokpo.capstonedesign.retrofit2;

public class RefreshToken {
    private String access;

    private String refresh;

    public String getAccess(){
        return access;
    }
    public void setAccess(String access){
        this.access = access;
    }

    public String getRefresh(){
        return  refresh;
    }
    public void setRefresh(){
        this.refresh = refresh;
    }
}
