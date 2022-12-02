package com.example.newzilla.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.newzilla.NewsDetails;
import com.example.newzilla.R;
import com.example.newzilla.bean.NewsBean;
import com.example.newzilla.utils.Methods;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AgriNewsAdapter extends RecyclerView.Adapter<AgriNewsAdapter.ViewHolder> {

    private final ArrayList<NewsBean> newsBeanArrayList;
    private Context context;

    public AgriNewsAdapter(Context context, ArrayList<NewsBean> newsBeanArrayList) {
        this.newsBeanArrayList = newsBeanArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.tv_title.setText(newsBeanArrayList.get(position).getTitle());
        holder.tv_desc.setText(Html.fromHtml(newsBeanArrayList.get(position).getDescription()));
        //Methods.glide_image_loader(newsBeanArrayList.get(position).getUrlToImage(),holder.iv_image);
        holder.tv_author.setText(newsBeanArrayList.get(position).getAuthor());

         try {

             //holder.tv_datetime.setText(newsBeanArrayList.get(position).getPublishedAt());
             holder.tv_datetime.setText(Methods.convertServerDateToUserTimeZone(newsBeanArrayList.get(position).getPublishedAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }




        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
        Glide.with(context)
                .load(newsBeanArrayList.get(position).getUrlToImage())
                .apply(requestOptions)
                .into(holder.iv_image);

        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri webpage = Uri.parse(newsBeanArrayList.get(position).getUrl());
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.newsBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_desc,tv_author,tv_datetime,tv_readmore;
        ImageView iv_image;
        LinearLayout ll_view;

        public ViewHolder(View v) {
            super(v);
            tv_title = v.findViewById(R.id.tv_title);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_author = v.findViewById(R.id.tv_author);
            iv_image = v.findViewById(R.id.iv_image);
            tv_datetime = v.findViewById(R.id.tv_datetime);
            tv_readmore = v.findViewById(R.id.tv_readmore);
            ll_view = v.findViewById(R.id.ll_view);

        }


    }

}