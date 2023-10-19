package com.mokpo.capstonedesign.retrofit2;

public class IngredientDeleteResponse {

        private String[] ingredient_ids;

        public String[] getIngredient_ids ()
        {
            return ingredient_ids;
        }

        public void setIngredient_ids (String[] ingredient_ids)
        {
            this.ingredient_ids = ingredient_ids;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [ingredient_ids = "+ingredient_ids+"]";
        }

}
