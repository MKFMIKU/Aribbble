package com.kfnoon.huanm.aribbble;

import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.ui.ShotActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int pages;
    private List<Shot> shotList = new ArrayList<Shot>();
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingView = (ProgressBar) findViewById(R.id.loading);
        mRecyclerView = (RecyclerView) findViewById(R.id.shotsList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        initData();
    }

    private void initUi(final List<Shot> shots) {
        loadingView.setVisibility(View.GONE);
        ShotsAdapter shotsAdapter = new ShotsAdapter(getApplicationContext(), shots);
        shotsAdapter.getPostionClicks().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                startActivity(new Intent(MainActivity.this, ShotActivity.class).putExtra("id", integer));
            }
        });
        mRecyclerView.setAdapter(shotsAdapter);
    }

    private void initData(){
        pages = 0;
        mSubscription = BaseClient.instance()
                .getShots(pages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Shot>>() {
                    @Override
                    public void call(List<Shot> shots) {
                        initUi(shots);
                    }
                });

    }
}
