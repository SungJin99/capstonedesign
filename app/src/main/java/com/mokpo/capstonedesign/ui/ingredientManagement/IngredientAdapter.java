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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.FoodViewHolder> {
    Context mContext;
    private List<IngredientResponse> foodList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
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
        int daysLeft = getDaysLeft(food.getExpiration_date());

        if (food.getExpiration_date() == null) {
            holder.tvHeader.setText("유통기한 없음");
        } else if (daysLeft < 0) {
            holder.tvHeader.setText("유통기한 만료");
        } else if (daysLeft == 0) {
            holder.tvHeader.setText("오늘까지입니다.");
        } else if (daysLeft == 1) {
            holder.tvHeader.setText("1일 남았습니다.");
        } else if (daysLeft > 1 && daysLeft < 3) {
            holder.tvHeader.setText("3일 미만 남았습니다.");
        } else if (daysLeft <= 3) {
            holder.tvHeader.setText("3일 이상 남았습니다.");
        } else {
            holder.tvHeader.setText("5일 이상 남았습니다.");
        }

    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
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
        TextView tvHeader;
        Button updateButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_header);
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
    private int getDaysLeft(String expirationDate) {
        if (expirationDate == null) {
            return Integer.MAX_VALUE; // or other default value
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expDate = null;
        try {
            expDate = sdf.parse(expirationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = expDate.getTime() - System.currentTimeMillis();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }


}

