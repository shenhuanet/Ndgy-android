package com.shenhua.nandagy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.shenhua.nandagy.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shenhua on 3/25/2017.
 * Email shenhuanet@126.com
 */
public class NineGridView extends GridView {

    private int[] images = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };
    private String[] titles = {
            "O2O收款",
            "订单查询",
            "转账",
            "手机充值",
            "信用卡还款",
            "水电煤",
            "违章代缴",
            "888",
            "更多"
    };
    private static final String TAG = "NineGridView";
    private boolean drawStroke = false;
    private int strokeColor = 0x8355802;
    private int strokeWidth = 1;

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

        int length = images.length;
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("ItemImage", images[i]);
            map.put("ItemText", titles[i]);
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.common_item_base_image_text,
                new String[]{"ItemImage", "ItemText"}, new int[]{R.id.imageView, R.id.textView});
        setAdapter(adapter);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getViews((ViewGroup) getRootView())) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
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
        localPaint.setColor(strokeColor);
        localPaint.setStrokeWidth(strokeWidth);

        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);

            if (drawStroke) {
                // 第一行
                if (i < 3)
                    canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getRight(), cellView.getTop(), localPaint);
                // 第一列
                if (i % column == 0) {
                    canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getLeft(), cellView.getBottom(), localPaint);
                }
                // 最后一列
                if ((i + 1) % column == 0)
                    canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(), localPaint);
                // 最右一行
                if ((i + 1) > (childCount - (childCount % column))) {
                    canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(), localPaint);
                }
            }

            // 无边框
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

    public void setDrawStroke(boolean drawStroke) {
        this.drawStroke = drawStroke;
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
}
