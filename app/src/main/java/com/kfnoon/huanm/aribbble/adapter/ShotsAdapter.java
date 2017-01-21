package com.kfnoon.huanm.aribbble.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.utils.StringUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class ShotsAdapter extends RecyclerView.Adapter<ShotsAdapter.MyViewHolder>{
    private List<Shot> shotList;
    private Context context;
    private final PublishSubject<Integer> onCLickSubject = PublishSubject.create();
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(context).load(shotList.get(position).images.normal).into(holder.shotImage);
        holder.shotTitle.setText(StringUtils.SubString(shotList.get(position).title, 12));
        holder.shotUser.setText(String.valueOf(shotList.get(position).user.name));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickSubject.onNext(shotList.get(position).id);
            }
        });
        holder.shotAnimated.setVisibility(shotList.get(position).animated?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return shotList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView shotImage;
        TextView shotTitle;
        TextView shotUser;
        TextView shotAnimated;
        RelativeLayout bottomList;

        public MyViewHolder(View inflate) {
            super(inflate);
            shotImage = (ImageView) inflate.findViewById(R.id.image_normal);
            shotTitle = (TextView) inflate.findViewById(R.id.shotTitle);
            shotUser = (TextView) inflate.findViewById(R.id.shotUser);
            bottomList = (RelativeLayout) inflate.findViewById(R.id.bottomList);
            shotAnimated = (TextView) inflate.findViewById(R.id.shotAnimated);
        }

    }

    public Observable<Integer> getPostionClicks(){
        return onCLickSubject.asObservable();
    }
}
