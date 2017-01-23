package com.kfnoon.huanm.aribbble.ui.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter;
import com.kfnoon.huanm.aribbble.api.BaseClient;
import com.kfnoon.huanm.aribbble.model.Shot;
import com.kfnoon.huanm.aribbble.ui.ShotActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainFragment extends Fragment {
    private ProgressBar loadingView;
    private RecyclerView mRecyclerView;
    private ShotsAdapter shotsAdapter;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Boolean loading;

    private int pages;
    private int postion;
    private List<Shot> shotList = new ArrayList<Shot>();
    private Subscription mSubscription;
    private Action1<Throwable> handleError;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        loadingView = (ProgressBar) view.findViewById(R.id.loading);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.shotsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        loading = false;
        initData();

        swipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisible = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible+1 == shotsAdapter.getItemCount() && !loading){
                    swipeRefreshLayout.setRefreshing(true);
                    postion = lastVisible;
                    loading = true;
                    updateData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisible = mLayoutManager.findLastVisibleItemPosition();
            }
        });

        return view;
    }



    //加载第一次数据
    private void initData(){
        pages = 1;
        mSubscription = BaseClient.instance()
                .getShots(pages)
                .doOnError(handleError)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Shot>>() {
                    @Override
                    public void call(List<Shot> shots) {
                        shotList = shots;
                        loadingView.setVisibility(View.GONE);
                        shotsAdapter = new ShotsAdapter(getContext(), shotList);
                        shotsAdapter.getPostionClicks().subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                startActivity(new Intent(getContext(), ShotActivity.class).putExtra("id", integer));
                            }
                        });
                        mRecyclerView.setAdapter(shotsAdapter);
                    }
                });
    }

    //上拉加载更多
    private void updateData(){
        pages++;
        mSubscription = BaseClient.instance()
                .getShots(pages)
                .doOnError(handleError)
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
