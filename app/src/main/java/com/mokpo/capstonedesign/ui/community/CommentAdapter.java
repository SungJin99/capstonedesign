package com.mokpo.capstonedesign.ui.community;


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
import com.mokpo.capstonedesign.ui.ingredientManagement.IngredientUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    Context mContext;
    private static ArrayList<Comment> commentList;

    public CommentAdapter(Context context, ArrayList<Comment> commentList) {
        this.mContext = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
       // holder.postId.setText(String.valueOf(post.getId()));
       // holder.postTitle.setText(post.getTitle());
      //  holder.postContent.setText(post.getContent());
       // holder.postDate.setText(post.getDate());
       // holder.postUser.setText(String.valueOf(post.getUser()));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView postId;
        TextView postTitle;
        TextView postContent;
        TextView postDate;
        TextView postUser;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.post_id);
            postTitle = itemView.findViewById(R.id.post_title);
            postContent = itemView.findViewById(R.id.post_content);
            postDate = itemView.findViewById(R.id.post_date);
            postUser= itemView.findViewById(R.id.post_user);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && mContext != null){
                        Intent intent = new Intent(mContext, PostDetailActivity.class);
                       // intent.putExtra("id", postList.get(position).getId());
                       // intent.putExtra("title", postList.get(position).getTitle());
                       // intent.putExtra("content", postList.get(position).getContent());
                       // intent.putExtra("date", postList.get(position).getDate());
                      //  intent.putExtra("user_id", postList.get(position).getUser());
                      //  intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }
}

