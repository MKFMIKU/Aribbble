package com.kfnoon.huanm.aribbble.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kfnoon.huanm.aribbble.MainActivity;
import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import rx.Observable;
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
        Picasso.with(context).load(shotList.get(position).images.normal).into(holder.shotImage);
        Picasso.with(context).load(shotList.get(position).images.normal).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Palette.Builder builder = new Palette.Builder(bitmap);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getMutedSwatch();
                        if(swatch!=null){
                            holder.bottomList.setBackgroundColor(swatch.getRgb());
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        holder.shotTitle.setText(StringUtils.SubString(shotList.get(position).title, 12));
        holder.shotUser.setText(String.valueOf(shotList.get(position).user.name));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickSubject.onNext(shotList.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shotList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView shotImage;
        TextView shotTitle;
        TextView shotUser;
        RelativeLayout bottomList;

        public MyViewHolder(View inflate) {
            super(inflate);
            shotImage = (ImageView) inflate.findViewById(R.id.image_normal);
            shotTitle = (TextView) inflate.findViewById(R.id.shotTitle);
            shotUser = (TextView) inflate.findViewById(R.id.shotUser);
            bottomList = (RelativeLayout) inflate.findViewById(R.id.bottomList);
        }

    }

    public Observable<Integer> getPostionClicks(){
        return onCLickSubject.asObservable();
    }
}
