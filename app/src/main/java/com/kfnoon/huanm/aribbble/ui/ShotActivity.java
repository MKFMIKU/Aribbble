package com.kfnoon.huanm.aribbble.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShotActivity extends Activity {

    private int ShotId;
    private Shot mShot;
    private Subscription mSubscription;
    private ImageView shotHidpi;
    private ProgressBar shotLoading;
    private TextView shotName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shot);

        Intent intent = getIntent();
        ShotId = intent.getIntExtra("id",0);

        shotHidpi = (ImageView) findViewById(R.id.shotHidpi);
        shotLoading = (ProgressBar) findViewById(R.id.shotLoading);
        shotName = (TextView) findViewById(R.id.shotName);

        initData();

        shotHidpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShotActivity.this, ShotImage.class).putExtra("url", mShot.images.hidpi));
            }
        });
    }

    private void initData() {
        mSubscription = BaseClient.instance()
                .getShot(ShotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Shot>() {
                    @Override
                    public void call(Shot shot) {
                        mShot = shot;
                        initUi();
                    }
                });
    }

    private void initUi(){
        shotName.setText(mShot.title);
        Glide.with(getApplicationContext())
                .load(mShot.images.hidpi)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        shotLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        shotLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .crossFade()
                .into(shotHidpi);

    }
}
