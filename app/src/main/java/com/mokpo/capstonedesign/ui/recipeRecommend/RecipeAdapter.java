package com.mokpo.capstonedesign.ui.recipeRecommend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.Recipe;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private final Context mContext;
    private final RecipeViewModel recipeViewModel;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList, RecipeViewModel recipeViewModel) {
        this.mContext = context;
        this.recipeList = recipeList;
        this.recipeViewModel = recipeViewModel;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.tvRecipeName.setText(recipe.getRCP_NM());

        // 클릭 리스너 설정
        holder.tvRecipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = ((TextView) v).getText().toString();
                recipeViewModel.fetchRecipeDetails(recipeName);  // 레시피 이름을 그대로 사용하여 요청을 보냅니다.
            }
        });



        // 이미지 URL이 제공되면 Picasso 라이브러리를 사용하여 이미지를 로드합니다.
        if (recipe.getImage_link() != null && !recipe.getImage_link().isEmpty()) {
            Picasso.get().load(recipe.getImage_link()).into(holder.ivRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void updateData(List<Recipe> newRecipes) {
        recipeList.clear();
        recipeList.addAll(newRecipes);
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        ImageView ivRecipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
        }
    }
}
