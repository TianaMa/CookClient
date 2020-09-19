package com.demo.baselib.view;

import android.graphics.Color;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class RcvDragVerticalCallback extends ItemTouchHelper.Callback {

    private RecyclerView recyclerView;

    private List dataList;

    public RcvDragVerticalCallback(RecyclerView recyclerView, List dataList) {
        this.recyclerView = recyclerView;
        this.dataList = dataList;
    }

    /**
     * 处理是否可以拖拽和滑动
     * dragFlags 拖拽方向 swipeFlags 滑动方向 均有四个反向 ItemTouchHelper.UP\DOWN\LEFT\RIGHT
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 处理拖拽的逻辑
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(dataList, from, to);
        recyclerView.getAdapter().notifyDataSetChanged();
        return true;
    }

    /**
     * 处理滑动的逻辑
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        dataList.remove(viewHolder.getAdapterPosition());
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     * actionState 三种状态 ：
     * ACTION_STATE_DRAG  拖动
     * ACTION_STATE_SWIPE 滑动
     * ACTION_STATE_SWIPE 停止
     * 一般 改变被拖拽的item 的背景色 在这里处理
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
    }

    /**
     * 当手指松开的时候（拖拽完成的时候）调用
     * 拖拽完成 回复背景色
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }
}