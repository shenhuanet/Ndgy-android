package com.shenhua.nandagy.module.acommon.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.ImageUtils;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.widget.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhua on 4/13/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_image_viewer,
        toolbarId = R.id.common_toolbar,
        toolbarTitleId = R.id.toolbar_title,
        toolbarHomeAsUp = true
)
public class ImageViewerActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    public static final String EXTRA_POSITION_KEY = "item";
    public static final String EXTRA_IMGS_KEY = "imgs";
    private boolean isFull = true;
    private List<String> imgs;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        imgs = getIntent().getStringArrayListExtra(EXTRA_IMGS_KEY);
        int mCurrentItem = 0;
        try {
            mCurrentItem = getIntent().getIntExtra("item", 0);
        } catch (Exception e) {
            mCurrentItem = 0;
        }
        if (imgs == null || imgs.size() == 0) {
            finish();
        }

        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(mCurrentItem);
        setupToolbarTitle(String.format("%d/" + imgs.size(), viewPager.getCurrentItem() + 1));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setupToolbarTitle(String.format("%d/" + imgs.size(), viewPager.getCurrentItem() + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getSupportActionBar().hide();
            isFull = true;
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getSupportActionBar().show();
            isFull = false;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_save) {
            ImageUtils.downLoadImageDefault(this, imgs.get(viewPager.getCurrentItem()), Constants.FileC.PICTURE_SAVE_DIR, true);
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.view_image_viewer, container, false);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.photo_loading);
            photoView.enable();
            photoView.enableRotate();
            ImageUtils.loadImageWithGlide(ImageViewerActivity.this, imgs.get(position), photoView, progressBar);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
