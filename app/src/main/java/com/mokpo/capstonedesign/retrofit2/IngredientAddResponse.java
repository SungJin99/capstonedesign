package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

public class IngredientAddResponse {

    private List<FoodItem> foodlist;

    public List<FoodItem> getInsertfood() {
        return foodlist;
    }

    public void setInsertfood(List<FoodItem> foodlist) {
        this.foodlist = foodlist;
    }

    public static class FoodItem {
        private String name;
        private String memo;
        private int count;

        private String expiration_info;

        private String manufacture_date;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
        public String getExpiration_info(){return expiration_info;}
        public void setExpiration_info(String expiration_info){this.expiration_info = expiration_info;}

        public String getManufacture_date(){return manufacture_date;}
        public void setManufacture_date(String manufacture_date){this.manufacture_date = manufacture_date;}
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }


    }
}
