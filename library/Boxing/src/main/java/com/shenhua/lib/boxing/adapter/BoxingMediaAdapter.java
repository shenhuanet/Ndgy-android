package com.shenhua.lib.boxing.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.lib.boxing.R;
import com.shenhua.lib.boxing.model.BoxingManager;
import com.shenhua.lib.boxing.model.config.BoxingConfig;
import com.shenhua.lib.boxing.model.entity.BaseMedia;
import com.shenhua.lib.boxing.model.entity.impl.ImageMedia;
import com.shenhua.lib.boxing.widget.MediaItemLayout;

import java.util.ArrayList;
import java.util.List;

public class BoxingMediaAdapter extends RecyclerView.Adapter {
    private static final int CAMERA_TYPE = 0;
    private static final int NORMAL_TYPE = 1;

    private int mOffset;
    private boolean mMultiImageMode;

    private List<BaseMedia> mMedias;
    private List<BaseMedia> mSelectedMedias;
    private LayoutInflater mInflater;
    private BoxingConfig mMediaConfig;
    private View.OnClickListener mOnCameraClickListener;
    private View.OnClickListener mOnMediaClickListener;
    private OnCheckListener mOnCheckListener;
    private OnMediaCheckedListener mOnCheckedListener;
    private Drawable mDefaultDrawable;

    public BoxingMediaAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mMedias = new ArrayList<>();
        this.mSelectedMedias = new ArrayList<>();
        this.mMediaConfig = BoxingManager.getInstance().getBoxingConfig();
        this.mOffset = mMediaConfig.isNeedCamera() ? 1 : 0;
        this.mMultiImageMode = mMediaConfig.getMode() == BoxingConfig.Mode.MULTI_IMG;
        this.mOnCheckListener = new OnCheckListener();
        this.mDefaultDrawable = ContextCompat.getDrawable(context, R.drawable.img_pic_loading_default);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mMediaConfig.isNeedCamera()) {
            return CAMERA_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (CAMERA_TYPE == viewType) {
            return new CameraViewHolder(mInflater.inflate(R.layout.layout_boxing_recycleview_header, parent, false));
        }
        return new ImageViewHolder(mInflater.inflate(R.layout.layout_boxing_recycleview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CameraViewHolder) {
            CameraViewHolder viewHolder = (CameraViewHolder) holder;
            viewHolder.mCameraLayout.setOnClickListener(mOnCameraClickListener);
        } else {
            int pos = position - mOffset;
            final BaseMedia media = mMedias.get(pos);
            final ImageViewHolder vh = (ImageViewHolder) holder;

            vh.mItemLayout.setDrawable(mDefaultDrawable);
            vh.mItemLayout.setTag(media);

            vh.mItemLayout.setOnClickListener(mOnMediaClickListener);
            vh.mItemLayout.setTag(R.id.media_item_check, pos);
            vh.mItemLayout.setMedia(media);
            vh.mItemChecked.setVisibility(mMultiImageMode ? View.VISIBLE : View.GONE);
            if (mMultiImageMode && media instanceof ImageMedia) {
                vh.mItemLayout.setChecked(((ImageMedia) media).isSelected());
                vh.mItemChecked.setTag(R.id.media_layout, vh.mItemLayout);
                vh.mItemChecked.setTag(media);
                vh.mItemChecked.setOnClickListener(mOnCheckListener);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mMedias.size() + mOffset;
    }

    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        mOnCameraClickListener = onCameraClickListener;
    }

    public void setOnCheckedListener(OnMediaCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }

    public void setOnMediaClickListener(View.OnClickListener onMediaClickListener) {
        mOnMediaClickListener = onMediaClickListener;
    }

    public List<BaseMedia> getSelectedMedias() {
        return mSelectedMedias;
    }

    public void setSelectedMedias(List<BaseMedia> selectedMedias) {
        if (selectedMedias == null) {
            return;
        }
        mSelectedMedias.clear();
        mSelectedMedias.addAll(selectedMedias);
    }

    public void addAllData(@NonNull List<BaseMedia> data) {
        this.mMedias.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(BaseMedia media) {
        this.mMedias.add(media);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mMedias.clear();
    }

    public List<BaseMedia> getAllMedias() {
        return mMedias;
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        MediaItemLayout mItemLayout;
        View mItemChecked;

        ImageViewHolder(View itemView) {
            super(itemView);
            mItemLayout = (MediaItemLayout) itemView.findViewById(R.id.media_layout);
            mItemChecked = itemView.findViewById(R.id.media_item_check);
        }
    }

    private static class CameraViewHolder extends RecyclerView.ViewHolder {
        View mCameraLayout;

        CameraViewHolder(final View itemView) {
            super(itemView);
            mCameraLayout = itemView.findViewById(R.id.camera_layout);
        }
    }

    private class OnCheckListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MediaItemLayout itemLayout = (MediaItemLayout) v.getTag(R.id.media_layout);
            BaseMedia media = (BaseMedia) v.getTag();
            if (mMediaConfig.getMode() == BoxingConfig.Mode.MULTI_IMG) {
                if (mOnCheckedListener != null) {
                    mOnCheckedListener.onChecked(itemLayout, media);
                }
            }
        }
    }

    public interface OnMediaCheckedListener {
        /**
         * In multi image mode, selecting a {@link BaseMedia} or undo.
         */
        void onChecked(View v, BaseMedia iMedia);
    }

}
