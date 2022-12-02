package com.example.newzilla.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newzilla.R;
import com.example.newzilla.UsersDetails;
import com.example.newzilla.bean.AccountBean;
import com.example.newzilla.bean.NewsBean;
import com.example.newzilla.utils.Methods;

import java.io.File;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final ArrayList<AccountBean> newsBeanArrayList;
    private Context context;
    File storage_path;

    public UsersAdapter(Context context, ArrayList<AccountBean> newsBeanArrayList) {
        this.newsBeanArrayList = newsBeanArrayList;
        this.context = context;
        storage_path= new File(context.getFilesDir(),"images");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.tv_name.setText(newsBeanArrayList.get(position).getmName());
        holder.tv_mobile_no.setText(newsBeanArrayList.get(position).getmMobileNo());

        File file = new File(storage_path,newsBeanArrayList.get(position).getmImage());
        //Methods.glide_image_loader(file.toString(),holder.iv_image);
        Glide.with(context).load(file).circleCrop().into(holder.iv_image);

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UsersDetails.class);
                intent.putExtra("mobile_no", newsBeanArrayList.get(position).getmMobileNo());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.newsBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_mobile_no;
        ImageView iv_image;
        LinearLayout ll_view;

        public ViewHolder(View v) {
            super(v);

            tv_name = v.findViewById(R.id.tv_name);
            iv_image = v.findViewById(R.id.iv_image);
            tv_mobile_no = v.findViewById(R.id.tv_mobile_no);
            ll_view = v.findViewById(R.id.ll_view);

        }


    }

}