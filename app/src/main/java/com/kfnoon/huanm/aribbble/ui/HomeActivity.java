package com.kfnoon.huanm.aribbble.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.kfnoon.huanm.aribbble.R;
import com.kfnoon.huanm.aribbble.ui.Fragments.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    ListView mDrawerList;
    ArrayList<HashMap<String, Object>> mMenuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        String[] mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mMenuList = new ArrayList<HashMap<String, Object>>();

        for(String title:mMenuTitles){
            HashMap<String, Object> t = new HashMap<String, Object>();
            t.put("image",R.drawable.ic_dribbble);
            t.put("title",title);
            mMenuList.add(t);
        }

        mDrawerList = (ListView) findViewById(R.id.listMenu);
        mDrawerList.setAdapter(new SimpleAdapter(this,mMenuList,R.layout.list_menu_row,
                new String[]{"image","title"},new int[]{R.id.imageViewIcon,R.id.textViewName}));
        mDrawerList.setDivider(null);

        MainFragment mainFragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame,mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return true;
    }
}
