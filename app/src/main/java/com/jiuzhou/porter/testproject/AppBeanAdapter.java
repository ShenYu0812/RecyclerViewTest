/*
 *  Copyright (c) 2016.  Project TestProject
 *  Source AppBeanAdapter
 *  Author 沈煜
 *  此源码及相关文档等附件由 沈煜 编写，作者保留所有权利
 *  使用必须注明出处。
 *  The code and documents is write by the author. All rights are reserved.
 *  Use must indicate the source.
 *
 */

package com.jiuzhou.porter.testproject;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppBeanAdapter extends RecyclerView.Adapter<AppBeanAdapter.ViewHolder> {
    private static final String TAG = AppBeanAdapter.class.getSimpleName();
    private List<AppBean> mList;
    private LayoutInflater inflater;
    private int currentPosition;
    private OnItemSelectedListener mItemSelectedListener;

    public AppBeanAdapter(Context mContext, List<AppBean> mList) {
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public AppBeanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.item_grid_apps, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final AppBeanAdapter.ViewHolder holder, int position) {
        holder.mImageView.setImageDrawable(mList.get(position).getAppIcon());
        holder.mTextView.setText(mList.get(position).getAppName());

        holder.itemView.setFocusable(true);
        holder.itemView.setTag(position);
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "焦点监听器被调用了");
                Log.d(TAG, "hasFocus=" + hasFocus);
                if (hasFocus) {
                    currentPosition = (int) holder.itemView.getTag();
                    Log.d(TAG, "getTag=" + currentPosition);
                    Log.i(TAG, "The view hasFocus=" + v + ", holder.itemView=" + holder.itemView);
                    mItemSelectedListener.OnItemSelected(v, currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.home_grid_item_icon);
            mTextView = (TextView) itemView.findViewById(R.id.home_grid_item_name);
        }
    }

    public interface OnItemSelectedListener {
        void OnItemSelected(View view, int position);
    }

    public void setOnItemSelectedListener (OnItemSelectedListener mItemSelectedListener) {
        this.mItemSelectedListener = mItemSelectedListener;
    }

}
