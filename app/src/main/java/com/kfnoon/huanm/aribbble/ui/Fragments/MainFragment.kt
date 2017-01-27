package com.kfnoon.huanm.aribbble.ui.Fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kfnoon.huanm.aribbble.adapter.ShotsAdapter

import com.kfnoon.huanm.aribbble.api.BaseClient
import com.kfnoon.huanm.aribbble.model.Shot


import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import kotlinx.android.synthetic.main.fragment_main.*
import rx.subscriptions.CompositeSubscription

class MainFragment : Fragment() {

    var lastVisible = 0
    var loading = false
    var postion = 0
    var pages = 0
    var mLayoutManager = GridLayoutManager(context, 2)
    var subscriptions = CompositeSubscription()
    var myShotList = listOf<Shot>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val subscription = BaseClient().getShots(pages++)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            shots->
                            myShotList = shots
                            (shotsList.adapter as ShotsAdapter).addShots(shots)
                        },
                        { e ->
                            Snackbar.make(shotsList, e.message ?: "", Snackbar.LENGTH_LONG).show()
                        }

                )
        subscriptions.add(subscription)

        swipeRefreshLayout.isEnabled = false
        shotsList.setHasFixedSize(true)
        shotsList.layoutManager = mLayoutManager

        shotsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisible + 1 == shotsList.adapter.itemCount && (!loading)) {
                    swipeRefreshLayout!!.isRefreshing = true
                    postion = lastVisible
                    loading = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisible = mLayoutManager.findLastVisibleItemPosition()
            }

        })

        return view
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

