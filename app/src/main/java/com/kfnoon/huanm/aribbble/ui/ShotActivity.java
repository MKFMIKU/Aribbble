package com.kfnoon.huanm.aribbble.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kfnoon.huanm.aribbble.R;

public class ShotActivity extends Activity {

    private int ShotId;
    private TextView shotView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot);

        Intent intent = getIntent();
        ShotId = intent.getIntExtra("id",0);

        shotView = (TextView) findViewById(R.id.ShotId);
        shotView.setText(ShotId+"h");
    }
}
