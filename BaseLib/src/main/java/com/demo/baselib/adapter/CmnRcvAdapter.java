package com.demo.baselib.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public abstract class CmnRcvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    private int mLayoutId;//item的layout
    private boolean showEmptyView = false;
    private MutableLiveData<List<T>> dataList = new MutableLiveData();//数据列表


    public CmnRcvAdapter(LifecycleOwner owner, int layoutId, MutableLiveData<List<T>> dataList) {
        this.mLayoutId = layoutId;
        this.dataList=dataList;
        this.dataList.observe(owner, ts -> {
            showEmptyView=true;
            notifyDataSetChanged();
        });
    }

    public CmnRcvAdapter(int layoutId, List<T> dataList) {
        this.mLayoutId = layoutId;
        this.dataList.setValue(dataList);
    }


    @Override
    public int getItemCount() {
        int size = dataList.getValue()==null?0:dataList.getValue().size();
        if(emptyView !=null &&showEmptyView&&size==0){
            return 1;
        }
        return size;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if(emptyView!=null&&showEmptyView&&viewType==VIEW_TYPE_EMPTY){
            return new EmptyViewHolder(emptyView,(RecyclerView) parent);
        }
        if(mIMultiItem!=null){
            int layoutId = mIMultiItem.getLayoutId(viewType);
            return CmnViewHolder.createViewHolder(parent, layoutId);
        }
        return CmnViewHolder.createViewHolder(parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EmptyViewHolder){
            return;
        }
        convert((CmnViewHolder)holder, dataList.getValue().get(position),position);
    }

    public abstract void convert(CmnViewHolder holder, T t, int position);


    /**
     * 公共视图的ViewHolder
     *
     * */
    public static class CmnViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews = new SparseArray();
        private View mConvertView;

        public CmnViewHolder(View itemView) {
            super(itemView);
            mConvertView = itemView;
        }

        public static CmnViewHolder createViewHolder(ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent,
                    false);
            return new CmnViewHolder(itemView);
        }

        /**
         * 通过viewId获取控件
         * @param viewId
         * @return View
         */
        public <V extends View> V getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (V) view;
        }
    }



    private View emptyView;

    public static final int VIEW_TYPE_EMPTY=-1;//空数据类型

    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
    }

    /**
     * 空数据视图的ViewHolder
     *
     * */
    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View emptyView,final RecyclerView parent){
            super(emptyView);
            RecyclerView.LayoutManager layoutManager =parent.getLayoutManager();
            if(layoutManager instanceof StaggeredGridLayoutManager){
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        new StaggeredGridLayoutManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(true);
                emptyView.setLayoutParams(layoutParams);
            }else if(layoutManager instanceof GridLayoutManager){
                final GridLayoutManager manager=(GridLayoutManager)layoutManager;
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type=((RecyclerView)parent).getAdapter().getItemViewType(position);
                        if(type== CmnRcvAdapter.VIEW_TYPE_EMPTY){
                            return manager.getSpanCount();
                        }
                        return 1;
                    }
                });
            }

        }
    }




    private IMultiItem<T> mIMultiItem;

    public CmnRcvAdapter(IMultiItem<T> mIMultiItem,List<T> dataList){
        this.mIMultiItem=mIMultiItem;
        this.dataList.postValue(dataList);
    }

    /**
     * 多种布局支持接口
     * 同一种数据在同一种布局管理器下的展示布局不同
     * 无法解决布局管理器不同的使用场景
     * 比如线性布局和网格布局交叉
     * */
    public interface IMultiItem<T> {
        int getLayoutId(int itemType);

        int getItemViewType(int position, T t);
    }


    @Override
    public int getItemViewType(int position) {
        int size = dataList.getValue()==null?0:dataList.getValue().size();
        if(size==0){
            return VIEW_TYPE_EMPTY;
        }
        if(mIMultiItem!=null){
            return mIMultiItem.getItemViewType(position, dataList.getValue().get(position));
        }
        return super.getItemViewType(position);
    }

}