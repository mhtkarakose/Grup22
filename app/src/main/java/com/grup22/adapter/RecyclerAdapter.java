package com.grup22.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.grup22.R;
import com.grup22.model.Data;

import java.util.List;

/**
 * Created by hp-pc on 18.11.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<mViewHolder> {
    private final Activity activity;
    private List<Data> posts;

    public RecyclerAdapter(Activity activity, List<Data> posts) {
        this.activity = activity;
        this.posts = posts;

    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(activity, activity.getLayoutInflater().inflate(R.layout.item_quiz, parent, false));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.bind(posts.get(position),position);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
