package com.kfnoon.huanm.aribbble.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.kfnoon.huanm.aribbble.R
import com.kfnoon.huanm.aribbble.model.Shot
import com.kfnoon.huanm.aribbble.utils.StringUtils

import rx.Observable
import rx.subjects.PublishSubject
import java.util.*

class ShotsAdapter(private val context: Context) : RecyclerView.Adapter<ShotsAdapter.MyViewHolder>() {
    private val onCLickSubject = PublishSubject.create<Int>()
    private var myShotItems: ArrayList<Shot> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.shot, parent, false)
        val vh = MyViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(myShotItems[position].images.normal).into(holder.shotImage)
        holder.shotTitle.text = StringUtils.SubString(myShotItems[position].title, 12)
        holder.shotUser.text = myShotItems[position].user.name
        holder.itemView.setOnClickListener { onCLickSubject.onNext(myShotItems[position].id) }
        holder.shotAnimated.visibility = if (myShotItems[position].animated) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return myShotItems.size
    }

    inner class MyViewHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {
        internal var shotImage: ImageView = inflate.findViewById(R.id.image_normal) as ImageView
        internal var shotTitle: TextView = inflate.findViewById(R.id.shotTitle) as TextView
        internal var shotUser: TextView = inflate.findViewById(R.id.shotUser) as TextView
        internal var shotAnimated: TextView = inflate.findViewById(R.id.shotAnimated) as TextView
    }

    fun addShots(shots: List<Shot>){
        val initPosition = myShotItems.size
        myShotItems.addAll(shots)
        notifyItemInserted(initPosition)
    }


    val postionClicks: Observable<Int>
        get() = onCLickSubject.asObservable()
}
