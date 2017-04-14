package com.shenhua.nandagy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.nandagy.R;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shenhua on 4/13/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_image_viewer,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true
)
public class ImageViewerActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView mImageView;
    @BindView(R.id.loading)
    ProgressBar mProgressBar;
    private PhotoViewAttacher mAttacher;
    private boolean isFull;
    private String url = "http://img02.tooopen.com/images/20141231/sy_78327074576.jpg";

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);

        setupToolbarTitle("图片详情");

        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setRotatable(true);
        mAttacher.setToRightAngle(true);

//        full(true);

        Glide.with(this).load(url).crossFade().into(new GlideDrawableImageViewTarget(mImageView) {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                full(!isFull);
            }

            @Override
            public void onOutsidePhotoTap() {

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
        if (item.getItemId() == R.id.menu_save) {
            mProgressBar.setVisibility(View.VISIBLE);
            BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<String>() {
                @Override
                public String doChildThread() {
                    try {
                        Bitmap b = Glide.with(ImageViewerActivity.this).load(url).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                        String title = url.substring(1 + url.lastIndexOf("/"), url.length());
                        return saveBitmapToSDCard(ImageViewerActivity.this, b, title, "ndgy", true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                public void doUiThread(String s) {
                    mProgressBar.setVisibility(View.INVISIBLE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAttacher != null) {
            mAttacher.cleanup();
            mAttacher = null;
            mImageView = null;
        }
    }
}
