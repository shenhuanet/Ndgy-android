package com.shenhua.nandagy.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.widget.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;

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
    private boolean isFull = true;
    private String[] imgs;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        imgs = getIntent().getStringArrayExtra("imgs");
        if (imgs == null || imgs.length == 0) {
            finish();
        }
        setupToolbarTitle(String.format("%d/" + imgs.length, viewPager.getCurrentItem() + 1));
        viewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setupToolbarTitle(String.format("%d/" + imgs.length, viewPager.getCurrentItem() + 1));
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
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_save) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("图片保存中...");
            dialog.show();
            BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<String>() {
                @Override
                public String doChildThread() {
                    try {
                        String url = imgs[viewPager.getCurrentItem()];
                        Bitmap b = Glide.with(ImageViewerActivity.this).load(url).asBitmap()
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        String title = url.substring(1 + url.lastIndexOf("/"), url.length());
                        return saveBitmapToSDCard(ImageViewerActivity.this, b, title, "ndgy", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                public void doUiThread(String s) {
                    dialog.dismiss();
                    if (s == null) {
                        toast("图片保存失败");
                    } else {
                        toast("图片已保存到：" + s);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: 4/13/2017  File file = new File(dir, title);
    private String saveBitmapToSDCard(Context context, Bitmap bitmap, String title, String dirName, boolean shouldRefreshGallery) throws Exception {
        File dir = new File(Environment.getExternalStorageDirectory(), dirName);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, title);
        if (!file.exists()) file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if (bitmap == null) throw new Exception("bitmap is null");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        if (shouldRefreshGallery)
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + dirName + file.getAbsolutePath())));
        return file.getAbsolutePath();
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.length;
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
            if (imgs[position].endsWith("gif")) {
                Glide.with(ImageViewerActivity.this).load(imgs[position]).asGif().crossFade()
                        .fitCenter().into(new Target<GifDrawable>() {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(Request request) {

                    }

                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });
            } else {
                Glide.with(ImageViewerActivity.this).load(imgs[position]).crossFade()
                        .fitCenter().into(new GlideDrawableImageViewTarget(photoView) {

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
