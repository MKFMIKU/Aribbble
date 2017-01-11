package com.kfnoon.huanm.aribbble.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kfnoon.huanm.aribbble.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShotImage extends AppCompatActivity {
    private String mshotUrl;
    private PhotoView mImageView;
    PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_image);
        Intent intent = getIntent();
        mshotUrl = intent.getStringExtra("url");

        mImageView = (PhotoView) findViewById(R.id.shotImage);
        mAttacher = new PhotoViewAttacher(mImageView);
        Glide.with(this).load(mshotUrl).into(mImageView);
        mAttacher.update();
    }
}
