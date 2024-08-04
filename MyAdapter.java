package com.example.student_attendance_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Profile> mList;
    private RecyclerViewClickListener listener;

    public MyAdapter(ArrayList<Profile> mList, RecyclerViewClickListener listener) {
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritem,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Profile vacancy1 = mList.get(position);
        holder.txtbname.setText("Student Name "+vacancy1.getName());
        holder.txtaddress.setText("Address "+vacancy1.getAddress());
        holder.txttype.setText("Year "+vacancy1.getShift());
        Glide.with(holder.img1.getContext())
                .asBitmap().load(vacancy1.getImageurl()).into(holder.img1);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView img1;
        TextView txtbname,txtaddress,txttype;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 =itemView.findViewById(R.id.img1);
            txtbname = itemView.findViewById(R.id.nametext);
            txtaddress = itemView.findViewById(R.id.coursetext);
            txttype = itemView.findViewById(R.id.emailtext);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }

}
