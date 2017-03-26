package com.shenhua.nandagy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;

import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.nandagy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhua on 3/25/2017.
 * Email shenhuanet@126.com
 */
public class NineGridView extends GridView {

    private int[] images;
    private String[] titles;
    private int strokeColor = 0x8355802;
    private int strokeWidth = 1;
    private OnItemClickListener listener;

    public NineGridView(Context context) {
        this(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setNumColumns(3);
        setHorizontalSpacing(0);
        setVerticalSpacing(0);
        setStretchMode(STRETCH_COLUMN_WIDTH);
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setNestedScrollingEnabled(false);
        }
    }

    public void setupNineGridView(int[] images, String[] titles) {
        this.images = images;
        this.titles = titles;
        setupNiniGridView();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    private void setupNiniGridView() {
        int length = images.length;
        List<BaseImageTextItem> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            BaseImageTextItem item = new BaseImageTextItem(images[i], titles[i]);
            list.add(item);
        }
        BaseListAdapter<BaseImageTextItem> adapter = new BaseListAdapter<BaseImageTextItem>(getContext(), list) {
            @Override
            public void onBindItemView(BaseViewHolder holder, BaseImageTextItem baseImageTextItem, int position) {
                holder.setText(R.id.textView, baseImageTextItem.getTitle());
                holder.setImageResource(R.id.imageView, baseImageTextItem.getDrawable());
                holder.getView(R.id.rootView).setOnClickListener(view -> {
                    if (listener != null)
                        listener.onClick(view, position);
                });
            }

            @Override
            public int getItemViewId() {
                return R.layout.common_item_base_image_text;
            }
        };
        setAdapter(adapter);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private boolean getViews(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return false;
        }
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof NestedScrollView || view instanceof ScrollView) {
                return true;

            } else if (view instanceof ViewGroup) {
                this.getViews((ViewGroup) view);
                return false;
            }
        }
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();
        int childCount = getChildCount();

        Paint localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorDivider));
        localPaint.setStrokeWidth(strokeWidth);

        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            if ((i + 1) % column == 0) {
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);// 底部
            } else if ((i + 1) > (childCount - (childCount % column))) {// 最后一行的item
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint); // 左
            } else {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint); // 左
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint); // 底部
            }
        }
        super.dispatchDraw(canvas);
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setImages(int[] images) {
        this.images = images;
    }

    public int[] getImages() {
        return images;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public String[] getTitles() {
        return titles;
    }
}
