package com.android.powerlifting.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.powerlifting.R;
import com.android.powerlifting.models.Post;
import com.android.powerlifting.ui.ImageDisplayActivity;
import com.android.powerlifting.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private ArrayList<Post> postsList;
//    OnItemClickListener listener;
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

        Picasso.get().load(currentPost.getUser().getProfilePhotoUrl()).into(holder.profilePic);
        Picasso.get().load(currentPost.getPhotoUrl()).into(holder.postPic);
        holder.caption.setText(currentPost.getCaption());
        holder.adminName.setText(currentPost.getUser().getName());

        //To show full image on click
        holder.postPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImageDisplayActivity.class);
                intent.putExtra("uri", currentPost.getPhotoUrl());
                v.getContext().startActivity(intent);
            }
        });

        holder.postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (listener != null && position != RecyclerView.NO_POSITION) {
//                    listener.onItemClick(position);
//                }

                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.postMenu);
                popupMenu.inflate(R.menu.recyclerview_menu);

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.edit:
                            Toast.makeText(v.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;

                        case R.id.delete:
                            Toast.makeText(v.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;

                        default:
                            return false;
                    }
                });
                popupMenu.show();
            }
        });

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
        Button postMenu;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.adminPicAdminPage);
            postPic = itemView.findViewById(R.id.post_pic);
            adminName = itemView.findViewById(R.id.adminName);
            time_place = itemView.findViewById(R.id.placeOfAdmin);
            caption = itemView.findViewById(R.id.caption_post);
            postMenu = itemView.findViewById(R.id.postMenu);

        }
    }

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        this.listener = listener;
//    }
}
