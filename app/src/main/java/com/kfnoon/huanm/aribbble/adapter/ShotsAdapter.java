package com.kfnoon.huanm.aribbble.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kfnoon.huanm.aribbble.MainActivity;
import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.MyViewHolder>{
    private List<Shot> shotList;
    private Context context;
    public ShotsAdapter(Context context,List<Shot> data){
        this.context = context;
        this.shotList = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shot, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context).load(shotList.get(position).images.normal).into(holder.shotImage);
        holder.shotTitle.setText(StringUtils.SubString(shotList.get(position).title, 16));
        holder.shotUser.setText(String.valueOf(shotList.get(position).user.name));
    }

    @Override
    public int getItemCount() {
        return shotList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView shotImage;
        TextView shotTitle;
        TextView shotUser;
        public MyViewHolder(View inflate) {
            super(inflate);
            shotImage = (ImageView) inflate.findViewById(R.id.image_normal);
            shotTitle = (TextView) inflate.findViewById(R.id.shotTitle);
            shotUser = (TextView) inflate.findViewById(R.id.shotUser);
        }
    }
}
