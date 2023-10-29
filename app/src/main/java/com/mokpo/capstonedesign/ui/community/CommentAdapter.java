package com.mokpo.capstonedesign.ui.community;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientUpdateActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> comments;
    private Context context;

    public CommentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_community_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
//        holder.writer.setText(comment.getUser());
//        holder.content.setText(comment.getContent());
//        holder.date.setText(comment.getDate());
        holder.cmtDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { new AlertDialog.Builder(v.getContext())
                    .setTitle("삭제 확인")
                    .setMessage("정말 삭제하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // '네' 버튼을 클릭했을 때 실행할 코드 작성.
                            deleteComment(comment.getId());
                            System.out.println(comment.getId());
                        }
                    })
                    .setNegativeButton("아니오", null)
                    .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView writer;
        private TextView content;
        private Button cmtDeleteButton;
        //private TextView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.cmt_content_tv);
            writer = itemView.findViewById(R.id.cmt_userid_tv);
            id = itemView.findViewById(R.id.cmt_date_tv);
            cmtDeleteButton = itemView.findViewById(R.id.cmt_delete_button);
        }
        public void bind(Comment comment){
            content.setText(comment.getContent());
            writer.setText(String.valueOf(comment.getUser()));
            id.setText(String.valueOf(comment.getId()));
        }
    }
    private void deleteComment(int commentId){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = sharedPreferences.getString("jwt_token", "");

        ApiService apiService = ApiClient.getApiService();
        Call<Void> call = apiService.deleteComment("Bearer " + accessToken, commentId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("MainActivity", "Data posted successfully.");
                    Toast.makeText(context, "댓글이 성공적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();  // Refresh adapter data after deletion.
                } else {
                    Toast.makeText(context, "댓글 삭제 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"에러: " + t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
