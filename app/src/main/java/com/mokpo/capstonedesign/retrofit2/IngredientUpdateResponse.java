package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

public class IngredientUpdateResponse {

    private List<FoodItem> foodlist;

    public List<FoodItem> getInsertfood() {
        return foodlist;
    }

    public void setInsertfood(List<FoodItem> foodlist) {
        this.foodlist = foodlist;
    }

    public static class FoodItem {
        private int id;
        private String name;
        private String memo;
        private int count;
        private String date;
        public int getId(){return id;}
        public void setId(int id){this.id = id;}

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
