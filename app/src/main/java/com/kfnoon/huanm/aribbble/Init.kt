package com.kfnoon.huanm.aribbble

import kotlinx.android.synthetic.main.activity_init.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.widget.SimpleAdapter
import com.kfnoon.huanm.aribbble.ui.Fragments.MainFragment
import java.util.*

class Init : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        var mainFragment = MainFragment()
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.contentFrame,mainFragment)
        fragmentTransaction.commit()

        setSupportActionBar(toolBar)
        var mDrawerToggle = ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState()
        drawer.addDrawerListener(mDrawerToggle)

        var mMenuList = listOf<HashMap<String, Any>>(
                hashMapOf(Pair("image",R.drawable.ic_dribbble), Pair("title","Popular")),
                hashMapOf(Pair("image",R.drawable.ic_dribbble), Pair("title","Recent")),
                hashMapOf(Pair("image",R.drawable.ic_dribbble), Pair("title","Debuts")),
                hashMapOf(Pair("image",R.drawable.ic_dribbble), Pair("title","Teams"))
        )
        listMenu.adapter = SimpleAdapter(this, mMenuList, R.layout.list_menu_row,
                arrayOf("image","title"), intArrayOf(R.id.imageViewIcon,R.id.textViewName))
//        listMenu.setOnItemClickListener { adapterView, view, i, l ->
//            when(i){
//                0 ->
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home, menu)
        return true
    }
}
