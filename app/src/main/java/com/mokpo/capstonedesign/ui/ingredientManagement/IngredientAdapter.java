package com.mokpo.capstonedesign.ui.ingredientManagement;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mokpo.capstonedesign.R;
import com.mokpo.capstonedesign.retrofit2.ApiClient;
import com.mokpo.capstonedesign.retrofit2.ApiService;
import com.mokpo.capstonedesign.retrofit2.IngredientResponse;
import com.mokpo.capstonedesign.retrofit2.IngredientUpdateRequest;
import com.mokpo.capstonedesign.retrofit2.IngredientUpdateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.FoodViewHolder> {
    Context mContext;
    private List<IngredientResponse> foodList;

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
        holder.tvIngredientName.setText("재료명: " + food.getName());
        holder.tvIngredientExpirationDate.setText(food.getExpiration_date());
        holder.tvIngredientQuantity.setText("수량: " + String.valueOf(food.getCount()));
        holder.tvIngredientMemo.setText("메모: " + food.getMemo());

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientName;
        TextView tvIngredientExpirationDate;
        TextView tvIngredientQuantity;
        TextView tvIngredientMemo;
        Button updateButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            tvIngredientExpirationDate = itemView.findViewById(R.id.tv_ingredient_expiration_date);
            tvIngredientQuantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tvIngredientMemo = itemView.findViewById(R.id.tv_ingredient_memo);
            updateButton = itemView.findViewById(R.id.updateButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mContext != null){
                        Intent intent = new Intent(mContext, IngredientUpdateActivity.class);
                        intent.putExtra("id", foodList.get(position).getId());
                        intent.putExtra("name", foodList.get(position).getName());
                        intent.putExtra("expiration_date", foodList.get(position).getExpiration_date());
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

