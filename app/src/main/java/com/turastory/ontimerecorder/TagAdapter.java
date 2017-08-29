package com.turastory.ontimerecorder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soldi on 2017-08-28.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private ArrayList<Tag> tagList;

    public TagAdapter(ArrayList<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_item_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tagText.setText( tagList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public void add(Tag tag, int position) {
        tagList.add(position, tag);
        notifyItemInserted(position);
    }

    public void add(Tag tag) {
        tagList.add(tag);
        notifyItemInserted(tagList.size() - 1);
    }

    public void remove(int position) {
        tagList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tag_text)
        TextView tagText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
