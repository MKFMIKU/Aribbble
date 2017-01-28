package com.kfnoon.huanm.aribbble.ui.Fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kfnoon.huanm.aribbble.R
import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter

import com.kfnoon.huanm.aribbble.api.BaseClient
import com.kfnoon.huanm.aribbble.model.Shot


import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import rx.subscriptions.CompositeSubscription

class MainFragment : Fragment() {

    var lastVisible = 0
    var loading = false
    var postion = 0
    var pages = 1
    var mLayoutManager = GridLayoutManager(context, 2)
    var subscriptions = CompositeSubscription()
    var myShotList = listOf<Shot>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        val shotsList = view.findViewById(R.id.shotsList) as RecyclerView
        val swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        val shotsAdapter = ShotsAdapter(context)

        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.isRefreshing = true
        shotsList.setHasFixedSize(true)
        shotsList.layoutManager = mLayoutManager
        shotsList.adapter = shotsAdapter

        requestShot(swipeRefreshLayout,shotsAdapter,shotsList)

        shotsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible + 1 == shotsList.adapter.itemCount && (!loading)) {
                    postion = lastVisible
                    loading = true
                    swipeRefreshLayout.isRefreshing = true
                    requestShot(swipeRefreshLayout,shotsAdapter,shotsList)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisible = mLayoutManager.findLastVisibleItemPosition()
            }

        })
        return view
    }

    fun requestShot(swipeRefreshLayout:SwipeRefreshLayout,shotsAdapter:ShotsAdapter,shotsList:RecyclerView) {
        Log.d("NetWork", pages.toString())

        val subscription = BaseClient().getShots(pages++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            shots->
                            loading = false
                            myShotList = shots
                            swipeRefreshLayout.isRefreshing = false
                            shotsAdapter.addShots(shots)
                        },
                        { e ->
                            e.printStackTrace()
                            Snackbar.make(shotsList, "NetWork Error", Snackbar.LENGTH_LONG).show()
                        }

                )
        subscriptions.add(subscription)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }
}

