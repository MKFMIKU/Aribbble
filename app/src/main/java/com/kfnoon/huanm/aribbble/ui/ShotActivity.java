package com.kfnoon.huanm.aribbble.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShotActivity extends Activity {

    private int ShotId;
    private Subscription mSubscription;
    private ImageView shotHidpi;
    private ProgressBar shotLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        Intent intent = getIntent();
        ShotId = intent.getIntExtra("id",0);

        shotHidpi = (ImageView) findViewById(R.id.shotHidpi);
        shotLoading = (ProgressBar) findViewById(R.id.shotLoading);

        initData();
    }

    private void initData() {
        int testId = 3200714;
        mSubscription = BaseClient.instance()
                .getShot(ShotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Shot>() {
                    @Override
                    public void call(Shot shot) {
                        initUi(shot);
                    }
                });
    }

    private void initUi(Shot mShot){
        shotLoading.setVisibility(View.GONE);
        if(mShot.animated)
            Glide.with(this).load(mShot.images.hidpi).asGif().into(shotHidpi);
        else
            Glide.with(this).load(mShot.images.hidpi).into(shotHidpi);
    }
}
