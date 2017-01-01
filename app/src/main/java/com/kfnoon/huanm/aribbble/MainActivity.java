package com.kfnoon.huanm.aribbble;

import com.kfnoon.huanm.aribbble.model.Shot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import netscape.javascript.JSObject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Shot> shotList = new ArrayList<Shot>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.shotsList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        initData();
    }

    private void initData(){
        
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
