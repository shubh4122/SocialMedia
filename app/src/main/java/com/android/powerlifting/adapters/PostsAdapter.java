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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        holder.profilePic.setImageResource(R.drawable.ic_launcher_background);
        holder.postPic.setImageResource(R.drawable.ic_launcher_background);
        holder.caption.setText(currentPost.getCaption());
        holder.adminName.setText(currentPost.getUser().getName());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMM, yyyy");
        String currentDateTime = sdf.format(new Date());//hh:mm dd MMM, yyyy

        String currentDate = currentDateTime.substring(6);//dd MMM, yyyy
        String postTime = currentPost.getTimeOfPost();//hh:mm dd MMM, yyyy
        if (postTime != null) {
            if (!currentDate.equalsIgnoreCase(postTime.substring(6)))
                postTime = postTime.substring(6);//dd MMM, yyyy
            else
                postTime = postTime.substring(0, 5);//hh:mm
        }
        holder.time_place.setText(postTime + " | " + currentPost.getLocationOfPostGenerator());

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

            profilePic = itemView.findViewById(R.id.adminPicAdminPage);
            postPic = itemView.findViewById(R.id.post_pic);
            adminName = itemView.findViewById(R.id.adminName);
            time_place = itemView.findViewById(R.id.placeOfAdmin);
            caption = itemView.findViewById(R.id.caption_post);

        }
    }
}
