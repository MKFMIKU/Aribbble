package com.kfnoon.huanm.aribbble;

import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

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
        mRecyclerView = (RecyclerView) findViewById(R.id.shotsList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        initData();
    }

    private void initUi(List<Shot> shots) {
        ShotsAdapter shotsAdapter = new ShotsAdapter(getApplicationContext(), shots);
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
