package com.kfnoon.huanm.aribbble;

import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.ui.ShotActivity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private ShotsAdapter shotsAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Boolean loading;

    private int pages;
    private List<Shot> shotList = new ArrayList<Shot>();
    private Subscription mSubscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingView = (ProgressBar) findViewById(R.id.loading);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.shotsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        loading = false;

        swipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisible = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible+1 == shotsAdapter.getItemCount() && !loading){
                    swipeRefreshLayout.setRefreshing(true);
                    updateData(lastVisible);
                    loading = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisible = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initUi() {
        loadingView.setVisibility(View.GONE);
        shotsAdapter = new ShotsAdapter(getApplicationContext(), shotList);
        shotsAdapter.getPostionClicks().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                startActivity(new Intent(MainActivity.this, ShotActivity.class).putExtra("id", integer));
            }
        });
        mRecyclerView.setAdapter(shotsAdapter);

    }

    private void initData(){
        pages = 1;
        mSubscription = BaseClient.instance()
                .getShots(pages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Shot>>() {
                    @Override
                    public void call(List<Shot> shots) {
                        shotList = shots;
                        initUi();
                    }
                });

    }

    private void updateData(final int postion){
        pages++;
        mSubscription = BaseClient.instance()
                .getShots(pages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Shot>>() {
                    @Override
                    public void call(List<Shot> shots) {
                        shotList.addAll(shots);
                        swipeRefreshLayout.setRefreshing(false);
                        shotsAdapter.notifyItemInserted(postion+1);
                        loading = false;
                    }
                });

    }
}
