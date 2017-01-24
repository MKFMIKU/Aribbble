package com.kfnoon.huanm.aribbble.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
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

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ShotActivity extends AppCompatActivity {

    private int ShotId;
    private Shot mShot;
    private Subscription mSubscription;
    private ImageView shotHidpi;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView shotName,shotAuthor,shotAuthorProfile,shotProfile;
    private CircleImageView shotAuthorAvatar;
    private Action1<Throwable> handleError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.shotToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent = getIntent();
        ShotId = intent.getIntExtra("id",0);

        shotHidpi = (ImageView) findViewById(R.id.shotHidpi);
        shotName = (TextView) findViewById(R.id.shotName);
        shotAuthor = (TextView) findViewById(R.id.shotAuthor);
        shotAuthorProfile = (TextView) findViewById(R.id.shotAuthorProfile);
        shotAuthorAvatar = (CircleImageView) findViewById(R.id.shotAuthorAvatar);
        shotProfile = (TextView) findViewById(R.id.shotProfile);

        initData();

        shotHidpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShotActivity.this, ShotImage.class).putExtra("url", mShot.images.hidpi));
            }
        });
    }

    private void initUi(){
        shotName.setText(mShot.title);
        shotAuthor.setText(mShot.user.name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            shotAuthorProfile.setText(Html.fromHtml(mShot.user.bio, Html.FROM_HTML_MODE_LEGACY));
            shotProfile.setText(Html.fromHtml(mShot.description, Html.FROM_HTML_MODE_LEGACY));
        }else{
            shotAuthorProfile.setText(Html.fromHtml(mShot.user.bio));
            shotProfile.setText(Html.fromHtml(mShot.description));
        }
        Glide.with(getApplicationContext()).load(mShot.user.avatar_url).into(shotAuthorAvatar);
        Glide.with(getApplicationContext())
                .load(mShot.images.hidpi)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(shotHidpi);
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
                        initUi();
                    }
                });
    }

    private static void handleError(Throwable throwable) {
        Log.d("SingleShot","Failed to load",throwable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shot, menu);

        return true;
    }

}
