package com.pmm.http.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pmm.http.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts){
        this.posts= posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post= posts.get(position);

        holder.userId.setText("User ID: " + post.getUserId());
        holder.id.setText("ID: " + post.getUserId());
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPost(List<Post> fetchedPosts) {
        this.posts= fetchedPosts;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView userId, id, title, body;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userId= itemView.findViewById(R.id.textUserId);
            id= itemView.findViewById(R.id.textId);
            title= itemView.findViewById(R.id.textTitle);
            body= itemView.findViewById(R.id.textBody);
        }
    }

}
