package com.mokpo.capstonedesign.ui.ingredientManagement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.IngredientResponse;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.FoodViewHolder> {

    private List<IngredientResponse> foodList;

    public IngredientAdapter(List<IngredientResponse> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        IngredientResponse food = foodList.get(position);
        holder.tvIngredientId.setText(String.valueOf(food.getId()));
        holder.tvIngredientName.setText(food.getName());
        holder.tvIngredientExpirationDate.setText(food.getDate());
        holder.tvIngredientQuantity.setText(String.valueOf(food.getCount()));
        holder.tvIngredientMemo.setText(food.getMemo());
        holder.tvIngredientUser.setText(String.valueOf(food.getUser()));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientId;
        TextView tvIngredientName;
        TextView tvIngredientExpirationDate;
        TextView tvIngredientQuantity;
        TextView tvIngredientMemo;
        TextView tvIngredientUser;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientId = itemView.findViewById(R.id.tv_ingredient_id);
            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            tvIngredientExpirationDate = itemView.findViewById(R.id.tv_ingredient_expiration_date);
            tvIngredientQuantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tvIngredientMemo = itemView.findViewById(R.id.tv_ingredient_memo);
            tvIngredientUser = itemView.findViewById(R.id.tv_ingredient_user);
        }
    }
}

