package com.mokpo.capstonedesign.ui.recipeRecommend;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecipeViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public RecipeViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecipeViewModel.class)) {
            return (T) new RecipeViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
