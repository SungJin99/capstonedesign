package com.mokpo.capstonedesign.ui.ingredientManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    Context mContext;
    private static List<IngredientResponse> foodList;

    public IngredientAdapter(Context context, List<IngredientResponse> foodList) {
        this.mContext = context;
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

    public class FoodViewHolder extends RecyclerView.ViewHolder {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mContext != null){
                        Intent intent = new Intent(mContext, IngredientUpdateActivity.class);
                        intent.putExtra("id", foodList.get(position).getId());
                        intent.putExtra("name", foodList.get(position).getName());
                        intent.putExtra("expirationdate", foodList.get(position).getDate());
                        intent.putExtra("quantity", foodList.get(position).getCount());
                        intent.putExtra("memo", foodList.get(position).getMemo());
                        intent.putExtra("user", foodList.get(position).getUser());
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }
}

