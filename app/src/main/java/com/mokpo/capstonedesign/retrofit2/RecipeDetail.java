package com.mokpo.capstonedesign.retrofit2;


import java.io.Serializable;

public class RecipeDetail implements Serializable {

    private String Manual_R;
    private String Manual_IMG_R;
    private String RCP_PARTS_DTLS;
    private String RCP_WAY2;
    private String INFO_NA;
    private String INFO_PRO;
    private String INFO_FAT;
    private String HASH_TAG;
    private String RCP_PAT2;

    private String ATT_FILE_NO_MAIN;
    private String INFO_CAR;
    private String RCP_NA_TIP;
    private String INFO_ENG;
    private String RCP_NM;

    // getter 메소드
    public String getManual_R() {
        return Manual_R;
    }

    public String getManual_IMG_R() {
        return Manual_IMG_R;
    }

    public String getRCP_PARTS_DTLS() {
        return RCP_PARTS_DTLS;
    }

    public String getRCP_WAY2() {
        return RCP_WAY2;
    }

    public String getINFO_NA() {
        return INFO_NA;
    }

    public String getINFO_PRO() {
        return INFO_PRO;
    }

    public String getINFO_FAT() {
        return INFO_FAT;
    }

    public String getHASH_TAG() {
        return HASH_TAG;
    }

    public String getRCP_PAT2() {
        return RCP_PAT2;
    }

    public String getATT_FILE_NO_MAIN() {
        return ATT_FILE_NO_MAIN;
    }

    public String getINFO_CAR() {
        return INFO_CAR;
    }

    public String getRCP_NA_TIP() {
        return RCP_NA_TIP;
    }

    public String getINFO_ENG() {
        return INFO_ENG;
    }

    public String getRCP_NM() {
        return RCP_NM;
    }

    // setter 메소드
    public void setManual_R(String manual_R) {
        Manual_R = manual_R;
    }

    public void setManual_IMG_R(String manual_IMG_R) {
        Manual_IMG_R = manual_IMG_R;
    }

    public void setRCP_PARTS_DTLS(String RCP_PARTS_DTLS) {
        this.RCP_PARTS_DTLS = RCP_PARTS_DTLS;
    }

    public void setRCP_WAY2(String RCP_WAY2) {
        this.RCP_WAY2 = RCP_WAY2;
    }

    public void setINFO_NA(String INFO_NA) {
        this.INFO_NA = INFO_NA;
    }

    public void setINFO_PRO(String INFO_PRO) {
        this.INFO_PRO = INFO_PRO;
    }

    public void setINFO_FAT(String INFO_FAT) {
        this.INFO_FAT = INFO_FAT;
    }

    public void setHASH_TAG(String HASH_TAG) {
        this.HASH_TAG = HASH_TAG;
    }

    public void setRCP_PAT2(String RCP_PAT2) {
        this.RCP_PAT2 = RCP_PAT2;
    }


    public void setATT_FILE_NO_MAIN(String ATT_FILE_NO_MAIN) {
        this.ATT_FILE_NO_MAIN = ATT_FILE_NO_MAIN;
    }

    public void setINFO_CAR(String INFO_CAR) {
        this.INFO_CAR = INFO_CAR;
    }

    public void setRCP_NA_TIP(String RCP_NA_TIP) {
        this.RCP_NA_TIP = RCP_NA_TIP;
    }

    public void setINFO_ENG(String INFO_ENG) {
        this.INFO_ENG = INFO_ENG;
    }

    public void setRCP_NM(String RCP_NM) {
        this.RCP_NM = RCP_NM;
    }
}

