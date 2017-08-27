package com.turastory.ontimerecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soldi on 2017-08-27.
 */

public class TagActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Tag> tagList;
    private TagAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);

        tagList = Database.getInstance().getTags();
        adapter = new TagAdapter(tagList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            boolean clicked = false;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View view = rv.findChildViewUnder(e.getX(), e.getY());

                if (!clicked && view != null && e.getAction() == MotionEvent.ACTION_MOVE) {
                    clicked = true;

                    int position = rv.getChildAdapterPosition(view);
                    setTagResult(position);
                    finish();

                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    clicked = false;
                }

                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void setTagResult(int position) {
        tagList.get(position);

        Intent intent = new Intent();
        intent.putExtra("tag", tagList.get(position));

        setResult(MainActivity.SUCCESS, intent);
    }
}
