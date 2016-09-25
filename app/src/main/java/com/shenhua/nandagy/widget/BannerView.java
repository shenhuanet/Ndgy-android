package com.shenhua.nandagy.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.nandagy.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Banner基类
 * Created by shenhua on 8/29/2016.
 */
public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private int bannerStyle = BannerViewConfig.NOT_INDICATOR;
    private int delayTime = 2000;
    private int mIndicatorSelectedResId = R.drawable.indicator_gray_circle;
    private int mIndicatorUnselectedResId = R.drawable.indicator_white_circle;
    private int titleHeight;
    private int titleBackground;
    private int titleTextColor;
    private int titleTextSize;
    private int defaultImage = -1;
    private int defaultErrorImage = -1;
    private int count = 0;
    private int currentItem;
    private int gravity = -1;
    private int lastPosition = 1;
    private boolean isAutoPlay = true;
    private List<ImageView> imageViews;
    private List<ImageView> indicatorImages;
    private Context context;
    private ViewPager viewPager;
    private LinearLayout indicator, indicatorInside, titleView;
    private Handler handler = new Handler();
    private OnBannerItemClickListener listener;
    private String[] titles;
    private TextView bannerTitle, numIndicatorInside, numIndicator;
    private BannerPagerAdapter adapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        imageViews = new ArrayList<>();
        indicatorImages = new ArrayList<>();
        initView();
    }

    private void initView() {
        imageViews.clear();
        View view = LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        titleView = (LinearLayout) view.findViewById(R.id.titleView);
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        indicatorInside = (LinearLayout) view.findViewById(R.id.indicatorInside);
        bannerTitle = (TextView) view.findViewById(R.id.bannerTitle);
        numIndicator = (TextView) view.findViewById(R.id.numIndicator);
        numIndicatorInside = (TextView) view.findViewById(R.id.numIndicatorInside);
        /**
         * 可以在此设置默认的配置
         */
        mIndicatorSelectedResId = R.drawable.indicator_gray_circle;
        mIndicatorUnselectedResId = R.drawable.indicator_white_circle;
        titleBackground = BannerViewConfig.TITLE_BACKGROUND;
        titleHeight = BannerViewConfig.TITLE_HEIGHT;
        titleTextColor = BannerViewConfig.TITLE_TEXT_COLOR;
        titleTextSize = BannerViewConfig.TITLE_TEXT_SIZE;
        defaultImage = R.drawable.img_default;
        defaultErrorImage = R.drawable.img_default;
    }

    private void initImages() {
        imageViews.clear();
        if (bannerStyle == BannerViewConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL ||
                bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL) {
            createIndicator();
        } else if (bannerStyle == BannerViewConfig.NUM_INDICATOR_TITLE) {
            numIndicatorInside.setText("1/" + count);
        } else if (bannerStyle == BannerViewConfig.NUM_INDICATOR) {
            numIndicator.setText("1/" + count);
        }
    }

    // 创建指示器
    private void createIndicator() {
        indicatorImages.clear();
        indicator.removeAllViews();
        indicatorInside.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int mIndicatorWidth = BannerViewConfig.INDICATOR_SIZE;
            int mIndicatorHeight = BannerViewConfig.INDICATOR_SIZE;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight);
            int mIndicatorMargin = BannerViewConfig.PADDING_SIZE;
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            if (bannerStyle == BannerViewConfig.CIRCLE_INDICATOR ||
                    bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL)
                indicator.addView(imageView, params);
            else if (bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL)
                indicatorInside.addView(imageView, params);
        }
    }

    // 开始自动播放
    private void startAutoPlay() {
        if (isAutoPlay) {
            handler.removeCallbacks(task);
            handler.postDelayed(task, delayTime);
        }
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if (isAutoPlay) {
                if (count > 1) {
                    currentItem = currentItem % (count + 1) + 1;
                    if (currentItem == 1) {
                        viewPager.setCurrentItem(currentItem, false);
                    } else {
                        viewPager.setCurrentItem(currentItem);
                    }
                    handler.postDelayed(task, delayTime);
                }
            }
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (count > 1) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isAutoPlay(false);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isAutoPlay(true);
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViews.get(position));
            final ImageView view = imageViews.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnBannerClick(v, position);
                    }
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (bannerStyle == BannerViewConfig.CIRCLE_INDICATOR ||
                bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL ||
                bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL) {
            indicatorImages.get((lastPosition - 1 + count) % count).setImageResource(mIndicatorUnselectedResId);
            indicatorImages.get((position - 1 + count) % count).setImageResource(mIndicatorSelectedResId);
            lastPosition = position;
        }
        if (position == 0) position = 1;
        switch (bannerStyle) {
            case BannerViewConfig.CIRCLE_INDICATOR:
                break;
            case BannerViewConfig.NUM_INDICATOR:
                if (position > count) position = count;
                numIndicator.setText(position + "/" + count);
                break;
            case BannerViewConfig.NUM_INDICATOR_TITLE:
                if (position > count) position = count;
                numIndicatorInside.setText(position + "/" + count);
                if (titles != null && titles.length > 0) {
                    if (position > titles.length) position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
            case BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL:
                if (titles != null && titles.length > 0) {
                    if (position > titles.length) position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
            case BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL:
                if (titles != null && titles.length > 0) {
                    if (position > titles.length) position = titles.length;
                    bannerTitle.setText(titles[position - 1]);
                }
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:
                isAutoPlay = false;
                break;
            case 2:
                isAutoPlay = true;
                break;
            case 0:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (viewPager.getCurrentItem() == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                currentItem = viewPager.getCurrentItem();
                isAutoPlay = true;
                break;
        }
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void setData() {
        currentItem = 1;
        if (adapter == null) {
            adapter = new BannerPagerAdapter();
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        viewPager.setFocusable(true);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(this);
        if (gravity != -1)
            indicator.setGravity(gravity);
        startAutoPlay();
    }

    // 是否自动播放
    public void isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        startAutoPlay();
    }

    /**
     * 设置banner的风格
     *
     * @param bannerStyle 0-5
     */
    public void setBannerStyle(@BannerViewConfig.BannerTypeChecker int bannerStyle) {
        this.bannerStyle = bannerStyle;
        switch (bannerStyle) {
            case BannerViewConfig.CIRCLE_INDICATOR:
                indicator.setVisibility(View.VISIBLE);
                break;
            case BannerViewConfig.NUM_INDICATOR:
                numIndicator.setVisibility(View.VISIBLE);
                break;
            case BannerViewConfig.NUM_INDICATOR_TITLE:
                numIndicatorInside.setVisibility(View.VISIBLE);
                break;
            case BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL:
                indicator.setVisibility(View.VISIBLE);
                break;
            case BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL:
                indicatorInside.setVisibility(VISIBLE);
                break;
            case BannerViewConfig.NOT_INDICATOR:
                break;
        }
    }

    /**
     * 设置标题数组
     *
     * @param titles 标题数组
     */
    public void setBannerTitleArray(String[] titles) {
        this.titles = titles;
        if (bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_VERTICAL ||
                bannerStyle == BannerViewConfig.NUM_INDICATOR_TITLE ||
                bannerStyle == BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL) {
            if (titleBackground != -1) {
                titleView.setBackgroundColor(titleBackground);
            }
            if (titleHeight != -1) {
                titleView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight));
            }
            if (titleTextColor != -1) {
                bannerTitle.setTextColor(titleTextColor);
            }
            if (titleTextSize != -1) {
                bannerTitle.setTextSize(titleTextSize);
            }
            if (titles != null && titles.length > 0) {
                bannerTitle.setText(titles[0]);
                bannerTitle.setVisibility(View.VISIBLE);
                titleView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置标题列表
     *
     * @param titles 标题列表
     */
    public void setBannerTitleList(List<String> titles) {
        setBannerTitleArray(titles.toArray(new String[titles.size()]));
    }

    /**
     * 设置图片数组
     *
     * @param imagesUrl 图片数组
     */
    public void setImageArray(Object[] imagesUrl) {
        if (imagesUrl == null || imagesUrl.length <= 0) {
            return;
        }
        count = imagesUrl.length;
        initImages();
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Object url;
            if (i == 0) {
                url = imagesUrl[count - 1];
            } else if (i == count + 1) {
                url = imagesUrl[0];
            } else {
                url = imagesUrl[i - 1];
            }
            imageViews.add(iv);
            if (defaultImage != -1)
                Glide.with(context).load(url).centerCrop().crossFade().placeholder(defaultImage).error(defaultImage).into(iv);
            else
                Glide.with(context).load(url).centerCrop().crossFade().placeholder(defaultImage).error(defaultErrorImage).into(iv);

        }
        setData();
    }

    /**
     * 设置图片列表
     *
     * @param imagesUrl 图片列表
     */
    public void setImageList(List<?> imagesUrl) {
        if (imagesUrl == null || imagesUrl.size() <= 0) {
            return;
        }
        count = imagesUrl.size();
        initImages();
        for (int i = 0; i <= count + 1; i++) {
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Object url;
            if (i == 0) {
                url = imagesUrl.get(count - 1);
            } else if (i == count + 1) {
                url = imagesUrl.get(0);
            } else {
                url = imagesUrl.get(i - 1);
            }
            imageViews.add(iv);
            if (defaultImage != -1)
                Glide.with(context).load(url).centerCrop().crossFade().placeholder(defaultImage).error(defaultImage).into(iv);
            else
                Glide.with(context).load(url).centerCrop().crossFade().placeholder(defaultImage).error(defaultErrorImage).into(iv);

        }
        setData();
    }

    // 设置显示时间
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    // 设置指示器对齐方式（左 中 右）
    public void setIndicatorGravity(int type) {
        switch (type) {
            case BannerViewConfig.LEFT:
                this.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                break;
            case BannerViewConfig.CENTER:
                this.gravity = Gravity.CENTER;
                break;
            case BannerViewConfig.RIGHT:
                this.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                break;
        }
    }

    // 设置默认图片
    public void setDefaultImage(int resId) {
        this.defaultImage = resId;
    }

    // 设置默认错误图片
    public void setDefaultErrorImage(int resId) {
        this.defaultErrorImage = resId;
    }

    // banner点击监听
    public void setOnBannerClickListener(OnBannerItemClickListener listener) {
        this.listener = listener;
    }

    // viewPager切换时监听
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public interface OnBannerItemClickListener {
        void OnBannerClick(View view, int position);
    }

    // 相关默认配置类
    public static class BannerViewConfig {
        /**
         * 指示器风格
         */
        @BannerTypeChecker
        public static final int NOT_INDICATOR = 0;// 无指示器（默认）
        @BannerTypeChecker
        public static final int CIRCLE_INDICATOR = 1;// 圆形指示器
        @BannerTypeChecker
        public static final int NUM_INDICATOR = 2;// 数字指示器
        @BannerTypeChecker
        public static final int NUM_INDICATOR_TITLE = 3;// 数字指示器和标题
        @BannerTypeChecker
        public static final int CIRCLE_INDICATOR_TITLE_VERTICAL = 4;// 圆形指示器和标题（上下）
        @BannerTypeChecker
        public static final int CIRCLE_INDICATOR_TITLE_HORIZONTAL = 5;// 圆形指示器和标题（左右）
        /**
         * 指示器位置对齐方式
         */
        public static final int LEFT = 5;// 左对齐
        public static final int CENTER = 6; // 居中对齐
        public static final int RIGHT = 7;// 右对齐
        /**
         * banner相关配置
         */
        public static final int INDICATOR_SIZE = 8;// 指示器大小
        public static final int PADDING_SIZE = 5;// 内边距
        /**
         * 标题风格
         */
        public static final int TITLE_BACKGROUND = -1;// 背景
        public static final int TITLE_HEIGHT = -1;// 高
        public static final int TITLE_TEXT_COLOR = -1;// 颜色
        public static final int TITLE_TEXT_SIZE = -1;// 字体大小

        @IntDef({NOT_INDICATOR, CIRCLE_INDICATOR, NUM_INDICATOR, NUM_INDICATOR_TITLE, CIRCLE_INDICATOR_TITLE_VERTICAL, CIRCLE_INDICATOR_TITLE_HORIZONTAL})
        @Retention(RetentionPolicy.SOURCE)
        public @interface BannerTypeChecker {

        }
    }
}
