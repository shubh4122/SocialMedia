package com.android.powerlifting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.powerlifting.R;
import com.android.powerlifting.models.Post;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private ArrayList<Post> postsList;
    public PostsAdapter(ArrayList<Post> postsList) {
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Post currentPost = postsList.get(position);

        //Get these pics from GLIDE, and db
//        holder.profilePic.setImageResource(R.drawable.ic_launcher_background);
//        holder.postPic.setImageResource(R.drawable.ic_launcher_background);

        holder.adminName.setText("ADMIN NAME");
        holder.time_place.setText("09:12 am | Kanpur, UP");
//        holder.caption.setText("GET TEXT AND PASTE!!");
    }

    @Override
    public int getItemCount() {
//        int itemCount = -1;
//        try {
//            itemCount = postsList.size();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return itemCount;
        return postsList.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic, postPic;
        TextView adminName, time_place, caption;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);
            postPic = itemView.findViewById(R.id.post_pic);
            adminName = itemView.findViewById(R.id.name_of_post_creator);
            time_place = itemView.findViewById(R.id.time_place_of_post);
            caption = itemView.findViewById(R.id.caption_post);

        }
    }
}
