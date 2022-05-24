package com.android.powerlifting.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.powerlifting.R;
import com.android.powerlifting.models.Member;
import com.android.powerlifting.ui.Member_profile_page;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {

    private ArrayList<Member> memberList;

    public MembersAdapter(ArrayList<Member> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public MembersAdapter.MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_card, parent, false);
        return new MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersAdapter.MembersViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Member member = memberList.get(position);
        holder.tvName.setText(member.getName());
        holder.tvLocation.setText(member.getLocation());

        // will set the image in member-card's profile pic area
        Picasso.get().load(member.getProfilePhotoUrl()).into(holder.profilePic);

        holder.btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(view.getContext(), Member_profile_page.class);
                intent.putExtra("memberPosition" , memberList.get(position));
                view.getContext().startActivity(intent);}
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void setFilteredList(ArrayList<Member> filteredList){
        this.memberList = filteredList;
        notifyDataSetChanged();
    }

    public class MembersViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView tvName, tvLocation;
        Button btnProfile;

        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.profilePic);

            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnProfile = itemView.findViewById(R.id.btnProfile);

        }
    }
}
