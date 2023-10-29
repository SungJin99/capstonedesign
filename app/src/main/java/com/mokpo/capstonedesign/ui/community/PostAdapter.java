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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context mContext;
    private static ArrayList<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> postList) {
        this.mContext = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        //게시글 조회 id값 제공
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PostDetailActivity.class);
                intent.putExtra("postId", post.getId());
                mContext.startActivity(intent);
            }
        });
        holder.postId.setText(String.valueOf(post.getId()));
        holder.postTitle.setText(post.getTitle());
        holder.postContent.setText(post.getContent());
        holder.postDate.setText(post.getDate());
        holder.postUser.setText(String.valueOf(post.getUser()));
        if (holder.commentRecyclerView != null) {
            List<Comment> comments = post.getComments();
            if (comments != null) {
                CommentAdapter commentAdapter = new CommentAdapter(comments);
                holder.commentRecyclerView.setAdapter(commentAdapter);
            } else {
                // 댓글이 없는 경우의 처리
                // 예: 댓글이 없습니다.
            }
        } else {
            // comment_recyclerview를 찾지 못한 경우의 처리
            // 예: 에러 메시지를 출력하거나 다른 동작을 수행합니다.
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postId;
        TextView postTitle;
        TextView postContent;
        TextView postDate;
        TextView postUser;

        RecyclerView commentRecyclerView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postId = itemView.findViewById(R.id.post_id);
            postTitle = itemView.findViewById(R.id.post_title);
            postContent = itemView.findViewById(R.id.post_content);
            postDate = itemView.findViewById(R.id.post_date);
            postUser= itemView.findViewById(R.id.post_user);
          //  commentRecyclerView = itemView.findViewById(R.id.comment_recyclerview);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION && mContext != null){
//                        Intent intent = new Intent(mContext, PostDetailActivity.class);
//                        intent.putExtra("id", postList.get(position).getId());
//                        intent.putExtra("title", postList.get(position).getTitle());
//                        intent.putExtra("content", postList.get(position).getContent());
//                        intent.putExtra("date", postList.get(position).getDate());
//                        intent.putExtra("user_id", postList.get(position).getUser());
//                        intent.putExtra("position", position);
//                        mContext.startActivity(intent);
//                    }
//                }
//            });
        }

    }
}

