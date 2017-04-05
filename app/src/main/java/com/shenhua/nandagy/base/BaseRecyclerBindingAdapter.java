package com.shenhua.nandagy.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 4/5/2017.
 * Email shenhuanet@126.com
 */
public abstract class BaseRecyclerBindingAdapter<T> extends RecyclerView.Adapter<BaseRecyclerBindingAdapter.RecyclerViewHolder> {

    private final int TYPE_NORMAL = 0X11;// Default Mode
    private final int TYPE_CUSTOM = 0X12;// Custom Mode
    private final int VIEW_HEADER = 0X20;// Header type
    private final int VIEW_BODYER = 0X00;// Bodyer type
    private final int VIEW_FOOTER = 0X22;// Footer type
    private final List<BaseRecyclerModel<T>> headViews;// Head data
    public final List<T> mData;// Body data
    private final List<BaseRecyclerModel<T>> footViews;// Foot data
    private int headerCount;
    private int footerCount;
    private int mMode;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnHeaderClickListener headerClickListener;
    private OnFooterClickListener footerClickListener;
    public ViewDataBinding binding;

    public BaseRecyclerBindingAdapter(List<T> mData) {
        this.mData = (mData != null) ? mData : new ArrayList<T>();
        headViews = new ArrayList<>();
        footViews = new ArrayList<>();
        headerCount = 0;
        if (0 != getStartMode()) {
            mMode = TYPE_CUSTOM;
        } else {
            mMode = TYPE_NORMAL;
        }
    }

    @Override
    public BaseRecyclerBindingAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null != headViews && viewType == VIEW_HEADER && getHeaderVisible()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getHeaderId(headerCount), parent, false);
            headerCount++;
        } else if (null != footViews && viewType == VIEW_FOOTER && getFooterVisible()) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getFooterId(footerCount), parent, false);
            footerCount++;
        } else {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutId(viewType), parent, false);
        }
        BaseRecyclerBindingAdapter.RecyclerViewHolder holder = new BaseRecyclerBindingAdapter.RecyclerViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerBindingAdapter.RecyclerViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_HEADER:
                bindData(holder, headViews.get(position).getVariableId(), headViews.get(position).getData());
                holder.itemView.setOnClickListener(getHeaderClickListener(holder.itemView, position));
                break;
            case VIEW_FOOTER:
                bindData(holder, footViews.get(getFooterPosition(holder)).getVariableId(), footViews.get(getFooterPosition(holder)).getData());
                holder.itemView.setOnClickListener(getFooterClickListener(holder.itemView, getFooterPosition(holder)));
                break;
            default:
                holder.itemView.setOnClickListener(getClickListener(holder.itemView, getRealPosition(holder)));
                holder.itemView.setOnLongClickListener(getLongClickListener(holder.itemView, getRealPosition(holder)));
                switch (mMode) {
                    case TYPE_NORMAL:
                        bindData(holder, getVariableId(getItemViewType(position)), mData.get(getRealPosition(holder)));
                        break;
                    case TYPE_CUSTOM:
                        bindData(holder, getVariableId(getItemViewType(position)), mData.get(getRealPosition(holder)));
                        bindCustomData(holder, position, mData.get(getRealPosition(holder)));
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == headViews) return getItemTypePosition(position);
        if (position < headViews.size()) return VIEW_HEADER;
        if (position < (getRealListSize() + headViews.size()))
            return getItemTypePosition(position - headViews.size());
        if (null != footViews && position < getItemCount()) return VIEW_FOOTER;
        return VIEW_BODYER;
    }

    /**
     * all data
     *
     * @return header + bodyer + footer
     */
    @Override
    public synchronized int getItemCount() {
        int size = 0;
        if (null != headViews) {
            size += headViews.size();
        }
        if (null != footViews) {
            size += footViews.size();
        }
        size += mData.size();
        return size;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_HEADER ? gridManager.getSpanCount() : getItemViewType(position) == VIEW_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerBindingAdapter.RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    private void bindData(BaseRecyclerBindingAdapter.RecyclerViewHolder holder, int variableId, T item) {
        holder.getBinding().setVariable(variableId, item);
        holder.getBinding().executePendingBindings();
    }

    /**
     * bodyer data
     *
     * @return body data size
     */
    public int getRealListSize() {
        return mData.size();
    }

    /**
     * bodyer data
     *
     * @param pos position
     * @return T content
     */
    public T getItemObject(int pos) {
        return mData.get(pos);
    }

    /**
     * get header layoutid
     *
     * @param position position
     * @return layoutid
     */
    private int getHeaderId(int position) {
        return headViews.get(position).getLayoutId();
    }

    private boolean getHeaderVisible() {
        return headerCount < headViews.size();
    }

    /**
     * get layoutid
     *
     * @param position position
     * @return layoutid
     */
    private int getFooterId(int position) {
        return footViews.get(position).getLayoutId();
    }

    private boolean getFooterVisible() {
        return footerCount < footViews.size();
    }

    /**
     * bodyer true position
     *
     * @param holder holder obj
     * @return true position
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headViews == null ? position : position - headViews.size();
    }

    private int getFooterPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return footViews == null ? position : position - (headViews.size() + getRealListSize());
    }

    /**
     * add bodyer data
     *
     * @param pos  position
     * @param item content
     */
    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyDataSetChanged();
    }

    /**
     * add bodyer data list
     *
     * @param items content
     */
    public void addBodyerList(List<T> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * remove bodyer data
     *
     * @param pos position
     */
    public void delete(int pos) {
        mData.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * set bodyer data list
     *
     * @param items content
     */
    public void setBodyerListData(List<T> items) {
        clearData();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * clear bodyer all data
     */
    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * add header data
     *
     * @param viewLayoutId view layoutid
     */
    public void addHeadView(BaseRecyclerModel viewLayoutId) {
        headViews.add(viewLayoutId);
        notifyDataSetChanged();
    }

    /**
     * remove header
     *
     * @param pos position
     */
    public void removeHeadView(int pos) {
        if (0 == headViews.size()) {
            return;
        }
        headerCount = 0;
        headViews.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * add footer data
     *
     * @param recyclerModel recyclermodel model
     */
    public void addFootView(BaseRecyclerModel recyclerModel) {
        footViews.add(recyclerModel);
        notifyDataSetChanged();
    }

    /**
     * remove footer data
     *
     * @param pos position
     */
    public void removeFootView(int pos) {
        if (0 == footViews.size()) {
            return;
        }
        footerCount = 0;
        footViews.remove(pos);
        notifyDataSetChanged();
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.onItemLongClickListener = itemLongClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setHeaderClickListener(OnHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
    }

    public void setFooterClickListener(OnFooterClickListener footerClickListener) {
        this.footerClickListener = footerClickListener;
    }

    private View.OnClickListener getClickListener(final View view, final int pos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(view, pos);
                }
            }
        };
    }

    private View.OnLongClickListener getLongClickListener(final View view, final int pos) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != onItemLongClickListener) {
                    onItemLongClickListener.onItemLongClick(view, pos);
                }
                return true;
            }
        };
    }

    private View.OnClickListener getHeaderClickListener(final View view, final int pos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != headerClickListener) {
                    headerClickListener.headerClick(view, pos);
                }
            }
        };
    }

    private View.OnClickListener getFooterClickListener(final View view, final int pos) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != footerClickListener) {
                    footerClickListener.footerClick(view, pos);
                }
            }
        };
    }

    /**
     * load mode
     *
     * @return 0 default
     */
    public abstract int getStartMode();

    /**
     * bodyer layoutid reference
     *
     * @param viewType type
     * @return layoutid
     */
    public abstract int getItemLayoutId(int viewType);

    /**
     * bind  reference
     *
     * @return BR id
     */
    public abstract int getVariableId(int viewType);

    public abstract int getItemTypePosition(int position);

    /**
     * custom
     *
     * @param holder   viewholder
     * @param position position
     * @param item     item
     */
    public abstract void bindCustomData(BaseRecyclerBindingAdapter.RecyclerViewHolder holder, int position, T item);

    public interface OnHeaderClickListener {
        void headerClick(View view, int position);
    }

    /**
     * footer click interface
     */
    public interface OnFooterClickListener {
        void footerClick(View view, int position);
    }

    /**
     * bodyer long click interface
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

    }

    public class BaseRecyclerModel<T> {
        private Integer layoutId;
        private Integer variableId;
        private T data;

        public BaseRecyclerModel() {

        }

        public BaseRecyclerModel(Integer layoutId, Integer variableId, T data) {
            this.layoutId = layoutId;
            this.variableId = variableId;
            this.data = data;
        }

        public Integer getLayoutId() {
            return layoutId;
        }

        public void setLayoutId(Integer layoutId) {
            this.layoutId = layoutId;
        }

        public Integer getVariableId() {
            return variableId;
        }

        public void setVariableId(Integer variableId) {
            this.variableId = variableId;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
