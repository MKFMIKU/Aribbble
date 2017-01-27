package com.kfnoon.huanm.aribbble.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.kfnoon.huanm.aribbble.R
import com.kfnoon.huanm.aribbble.model.Shot
import com.kfnoon.huanm.aribbble.utils.StringUtils

import rx.Observable
import rx.subjects.PublishSubject

class ShotsAdapter(private val context: Context, private val shotList: List<Shot>) : RecyclerView.Adapter<ShotsAdapter.MyViewHolder>() {
    private val onCLickSubject = PublishSubject.create<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.shot, parent, false)
        val vh = MyViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(shotList[position].images!!.normal).into(holder.shotImage)
        holder.shotTitle.text = StringUtils.SubString(shotList[position].title, 12)
        holder.shotUser.text = shotList[position].user!!.name.toString()
        holder.itemView.setOnClickListener { onCLickSubject.onNext(shotList[position].id) }
        holder.shotAnimated.visibility = if (shotList[position].animated!!) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return shotList.size
    }

    inner class MyViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        internal var shotImage: ImageView
        internal var shotTitle: TextView
        internal var shotUser: TextView
        internal var shotAnimated: TextView
        internal var bottomList: RelativeLayout

        init {
            shotImage = inflate.findViewById(R.id.image_normal) as ImageView
            shotTitle = inflate.findViewById(R.id.shotTitle) as TextView
            shotUser = inflate.findViewById(R.id.shotUser) as TextView
            bottomList = inflate.findViewById(R.id.bottomList) as RelativeLayout
            shotAnimated = inflate.findViewById(R.id.shotAnimated) as TextView
        }

    }

    val postionClicks: Observable<Int>
        get() = onCLickSubject.asObservable()
}
