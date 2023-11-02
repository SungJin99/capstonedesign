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
    public void refresh() {
        notifyDataSetChanged();
    }
    public class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvIngredientId;
        TextView tvIngredientName;
        TextView tvIngredientExpirationDate;
        TextView tvIngredientQuantity;
        TextView tvIngredientMemo;
        TextView tvIngredientUser;
        Button updateButton;
        EditText id;
        EditText name;
        EditText expiry;
        EditText quantity;
        EditText memo;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIngredientId = itemView.findViewById(R.id.tv_ingredient_id);
            tvIngredientName = itemView.findViewById(R.id.tv_ingredient_name);
            tvIngredientExpirationDate = itemView.findViewById(R.id.tv_ingredient_expiration_date);
            tvIngredientQuantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            tvIngredientMemo = itemView.findViewById(R.id.tv_ingredient_memo);
            tvIngredientUser = itemView.findViewById(R.id.tv_ingredient_user);
            updateButton = itemView.findViewById(R.id.updateButton);
            id = itemView.findViewById(R.id.idEditText);
            name = itemView.findViewById(R.id.nameEditText);
            expiry = itemView.findViewById(R.id.expiryEditText);
            quantity = itemView.findViewById(R.id.quantityTextView);
            memo = itemView.findViewById(R.id.memoEditText);
//            updateButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new AlertDialog.Builder(v.getContext())
//                            .setTitle("수정 확인")
//                            .setMessage("정말 수정하시겠습니까?")
//                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // '네' 버튼을 클릭했을 때 실행할 코드 작성.
//                                    int idValue = Integer.parseInt(id.getText().toString());
//                                    String nameValue = name.getText().toString();
//                                    String expiryValue = expiry.getText().toString();
//                                    int quantityValue = Integer.parseInt(quantity.getText().toString());
//                                    String memoValue = memo.getText().toString();
//
//                                    IngredientUpdateRequest request = new IngredientUpdateRequest(idValue, nameValue, expiryValue, quantityValue, memoValue);
//                                    sendUpdateFoodList(request);
//                                }
//                            })
//                            .setNegativeButton("아니오", null)
//                            .show();
//                }
//            });
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
//    private void sendUpdateFoodList(IngredientUpdateRequest updateRequest) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        String accessToken = sharedPreferences.getString("jwt_token", "");
//
//        ApiService apiService = ApiClient.getApiService();
//
//        Call<IngredientUpdateResponse> call = apiService.getUpdateFood("Bearer " + accessToken,
//                updateRequest.getId(),
//                updateRequest.getName(),
//                updateRequest.getMemo(),
//                updateRequest.getCount(),
//                updateRequest.getDate());
//
//        call.enqueue(new Callback<IngredientUpdateResponse>() {
//            @Override
//            public void onResponse(Call<IngredientUpdateResponse> call, retrofit2.Response<IngredientUpdateResponse> response) {
//                if (response.isSuccessful()) {
//                    Log.i("MainActivity", "Data update successfully.");
//                    Toast.makeText(mContext, "식재료가 성공적으로 수정되었습니다..", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//
//                } else {
//                    Log.e("MainActivity", "Error updating data: " + response.code());
//                    Toast.makeText(mContext, "식재료 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<IngredientUpdateResponse> call, Throwable t) {
//                Log.e("MainActivity", "Error: " + t.getMessage());
//                Toast.makeText(mContext, "네트워크에 문제가 있습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}

