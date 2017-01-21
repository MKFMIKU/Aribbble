package com.kfnoon.huanm.aribbble.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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
    private TextView shotName,shotDescription;
    private Action1<Throwable> handleError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shot);

        Intent intent = getIntent();
        ShotId = intent.getIntExtra("id",0);

        shotHidpi = (ImageView) findViewById(R.id.shotHidpi);
        shotLoading = (ProgressBar) findViewById(R.id.shotLoading);
        shotName = (TextView) findViewById(R.id.shotName);
        shotDescription = (TextView) findViewById(R.id.shotDescription);

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
                .doOnError(this.handleError)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Shot>() {
                    @Override
                    public void call(Shot shot) {
                        mShot = shot;
                        shotName.setText(mShot.title);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            shotDescription.setText(Html.fromHtml(mShot.description, Html.FROM_HTML_MODE_LEGACY));
                        }else{
                            shotDescription.setText(Html.fromHtml(mShot.description));
                        }
                        Glide.with(getApplicationContext())
                                .load(mShot.images.hidpi)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
                                .into(shotHidpi);
                    }
                });
    }

    private static void handleError(Throwable throwable) {
        Log.d("SingleShot","Failed to load",throwable);
    }

}
