package com.kfnoon.huanm.aribbble.ui.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.kfnoon.huanm.aribbble.R
import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter
import com.kfnoon.huanm.aribbble.api.BaseClient
import com.kfnoon.huanm.aribbble.model.Shot
import com.kfnoon.huanm.aribbble.ui.ShotActivity

import java.util.ArrayList

import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

class MainFragment : Fragment() {
    private var loadingView: ProgressBar? = null
    private var mRecyclerView: RecyclerView? = null
    private var shotsAdapter: ShotsAdapter? = null
    private var mLayoutManager: GridLayoutManager? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var loading: Boolean? = null

    private var pages: Int = 0
    private var postion: Int = 0
    private var shotList: MutableList<Shot> = ArrayList()
    private var mSubscription: Subscription? = null
    private val handleError: Action1<Throwable>? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        loadingView = view.findViewById(R.id.loading) as ProgressBar
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        mRecyclerView = view.findViewById(R.id.shotsList) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(context, 2)
        mRecyclerView!!.layoutManager = mLayoutManager

        loading = false
        initData()

        swipeRefreshLayout!!.isEnabled = false
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            internal var lastVisible = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible + 1 == shotsAdapter!!.itemCount && (!loading!!)!!) {
                    swipeRefreshLayout!!.isRefreshing = true
                    postion = lastVisible
                    loading = true
                    updateData()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisible = mLayoutManager!!.findLastVisibleItemPosition()
            }
        })

        return view
    }


    //加载第一次数据
    private fun initData() {
        var client = BaseClient()
        pages = 1
        mSubscription = client
                .getShots(pages)
                .doOnError(handleError)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action1 { shots ->
                    shotList = shots as MutableList<Shot>
                    loadingView!!.visibility = View.GONE
                    shotsAdapter = ShotsAdapter(context, shotList)
                    shotsAdapter!!.postionClicks.subscribe { integer -> startActivity(Intent(context, ShotActivity::class.java).putExtra("id", integer)) }
                    mRecyclerView!!.adapter = shotsAdapter
                })
    }

    //上拉加载更多
    private fun updateData() {
        var client = BaseClient()
        pages++
        mSubscription = client
                .getShots(pages)
                .doOnError(handleError)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Action1 { shots ->
                    shotList.addAll(shots)
                    swipeRefreshLayout!!.isRefreshing = false
                    shotsAdapter!!.notifyItemInserted(postion + 1)
                    loading = false
                })
    }
}// Required empty public constructor
