package com.turastory.ontimerecorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import com.squareup.otto.Subscribe;
import com.turastory.ontimerecorder.bus.AddTagEvent;
import com.turastory.ontimerecorder.bus.Bus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * Created by soldi on 2017-08-27.
 */

public class TagActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.add_tag_button)
    Button addTagButton;

    private ArrayList<Tag> tagList;
    private TagAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        ButterKnife.bind(this);
        Bus.register(this);

        setupRecyclerView();

        addTagButton.setOnClickListener(v -> {
            DialogFragment newFragment = AddTagDialogFragment.newInstance();
            newFragment.show(getSupportFragmentManager(), "dialog");
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.unregister(this);
    }

    private void setupRecyclerView() {
        tagList = Database.getInstance().getTags();
        adapter = new TagAdapter(tagList);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
//        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            boolean clicked = false;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View view = rv.findChildViewUnder(e.getX(), e.getY());

                // 이거 이렇게 하면 스크롤도 안됨 ㄷㄷ
                if (!clicked && view != null && e.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.e(getClass().getName(), "Clickk");
                    clicked = true;

                    new Handler().postDelayed(() -> {
                        int position = rv.getChildAdapterPosition(view);
                        setTagResult(position);
                        finish();
                    }, 150);

                } else if (e.getAction() == MotionEvent.ACTION_UP) {
                    Log.e(getClass().getName(), "Release");
                    clicked = false;
                }

                // 어째서인가 true를 리턴하면 ripple effect가 적용되지 않는다.
                return false;
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

    // Called from AddTagDialogFragment
    @Subscribe
    public void onAddTagSubmit(AddTagEvent event) {
        addNewTag(event.getTagName());
    }

    private void addNewTag(String name) {
        Tag tag = new Tag(name);

        adapter.add(tag);
        Database.getInstance().addTag(tag);
    }
}
