package com.shenhua.photopicker.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.shenhua.photopicker.Crop;
import com.shenhua.photopicker.R;
import com.shenhua.photopicker.utils.CropUtil;
import com.shenhua.photopicker.utils.GraphicsUtil;
import com.shenhua.photopicker.widget.CircleHighlightView;
import com.shenhua.photopicker.widget.CropImageView;
import com.shenhua.photopicker.widget.HighlightView;
import com.shenhua.photopicker.widget.ImageViewTouchBase;
import com.shenhua.photopicker.widget.RotateBitmap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class CropImageActivity extends MonitoredActivity {

    private static final boolean IN_MEMORY_CROP = Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1;
    private static final int SIZE_DEFAULT = 2048;
    private static final int SIZE_LIMIT = 4096;
    private final Handler handler = new Handler();
    private int aspectX;
    private int aspectY;
    private int maxX;
    private int maxY;
    private int exifRotation;
    private Uri sourceUri;
    private Uri saveUri;
    private boolean isSaving;
    private int sampleSize;
    private RotateBitmap rotateBitmap;
    private CropImageView imageView;
    private HighlightView cropView;
    private boolean isCircleCrop = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_photo_crop);
        initViews();
        setupFromIntent();
        if (rotateBitmap == null) {
            finish();
            return;
        }
        startCrop();
    }

    private void initViews() {
        imageView = (CropImageView) findViewById(R.id.crop_image);
        imageView.context = this;
        imageView.setRecycler(new ImageViewTouchBase.Recycler() {

            @Override
            public void recycle(Bitmap b) {
                b.recycle();
                System.gc();
            }
        });
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) return;
        TextView toolBarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolBarTitle.setText(R.string.title_crop);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle("");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
    }

    private void onSaveClicked() {
        if (cropView == null || isSaving) {
            return;
        }
        isSaving = true;
        Bitmap croppedImage = null;
        Rect r = cropView.getScaledCropRect(sampleSize);
        int width = r.width();
        int height = r.height();
        int outWidth = width, outHeight = height;
        if (maxX > 0 && maxY > 0 && (width > maxX || height > maxY)) {
            float ratio = (float) width / (float) height;
            if ((float) maxX / (float) maxY > ratio) {
                outHeight = maxY;
                outWidth = (int) ((float) maxY * ratio + .5f);
            } else {
                outWidth = maxX;
                outHeight = (int) ((float) maxX / ratio + .5f);
            }
        }
        if (IN_MEMORY_CROP && rotateBitmap != null) {
            croppedImage = inMemoryCrop(rotateBitmap, null, r, width,
                    height, outWidth, outHeight);
            if (croppedImage != null) {
                imageView.setImageBitmapResetBase(croppedImage, true);
                imageView.center(true, true);
                imageView.highlightViews.clear();
            }
        } else {
            try {
                croppedImage = decodeRegionCrop(null, r);
                if (isCircleCrop) {
                    croppedImage = cropCircleView(croppedImage);
                }
            } catch (IllegalArgumentException e) {
                setResultException(e);
                finish();
                return;
            }

            if (croppedImage != null) {
                imageView.setImageRotateBitmapResetBase(new RotateBitmap(
                        croppedImage, exifRotation), true);
                imageView.center(true, true);
                imageView.highlightViews.clear();
            }
        }
        saveImage(croppedImage);
    }

    private void setupFromIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            aspectX = extras.getInt(Crop.Extra.ASPECT_X);
            aspectY = extras.getInt(Crop.Extra.ASPECT_Y);
            maxX = extras.getInt(Crop.Extra.MAX_X);
            maxY = extras.getInt(Crop.Extra.MAX_Y);
            isCircleCrop = extras.getBoolean(Crop.Extra.IS_CIRCLE_CROP);
            saveUri = extras.getParcelable(MediaStore.EXTRA_OUTPUT);
        }
        sourceUri = intent.getData();
        if (sourceUri != null) {
            exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(getContentResolver(), sourceUri));
            InputStream is = null;
            try {
                sampleSize = calculateBitmapSampleSize(sourceUri);
                is = getContentResolver().openInputStream(sourceUri);
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inSampleSize = sampleSize;
                rotateBitmap = new RotateBitmap(BitmapFactory.decodeStream(is,
                        null, option), exifRotation);
            } catch (IOException | OutOfMemoryError e) {
                setResultException(e);
            } finally {
                CropUtil.closeSilently(is);
            }
        }
    }

    private int calculateBitmapSampleSize(Uri bitmapUri) throws IOException {
        InputStream is = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            is = getContentResolver().openInputStream(bitmapUri);
            BitmapFactory.decodeStream(is, null, options);
        } finally {
            CropUtil.closeSilently(is);
        }
        int maxSize = getMaxImageSize();
        int sampleSize = 1;
        while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }

    private int getMaxImageSize() {
        int textureLimit = getMaxTextureSize();
        if (textureLimit == 0) {
            return SIZE_DEFAULT;
        } else {
            return Math.min(textureLimit, SIZE_LIMIT);
        }
    }

    private int getMaxTextureSize() {
        int[] maxSize = new int[1];
        GLES10.glGetIntegerv(GLES10.GL_MAX_TEXTURE_SIZE, maxSize, 0);
        return maxSize[0];
    }

    private void startCrop() {
        if (isFinishing()) {
            return;
        }
        imageView.setImageRotateBitmapResetBase(rotateBitmap, true);
        CropUtil.startBackgroundJob(this, null, "请稍候...", new Runnable() {

            public void run() {
                final CountDownLatch latch = new CountDownLatch(1);
                handler.post(new Runnable() {

                    public void run() {
                        if (imageView.getScale() == 1F) {
                            imageView.center(true, true);
                        }
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                new Cropper().crop();
            }
        }, handler);
    }

    private class Cropper {

        private void makeDefault() {
            if (rotateBitmap == null) {
                return;
            }
            HighlightView hv = isCircleCrop ? new CircleHighlightView(imageView) : new HighlightView(imageView);
            final int width = rotateBitmap.getWidth();
            final int height = rotateBitmap.getHeight();
            Rect imageRect = new Rect(0, 0, width, height);
            int cropWidth = Math.min(width, height) * 4 / 5;
            int cropHeight = cropWidth;
            if (!isCircleCrop && aspectX != 0 && aspectY != 0) {
                if (aspectX > aspectY) {
                    cropHeight = cropWidth * aspectY / aspectX;
                } else {
                    cropWidth = cropHeight * aspectX / aspectY;
                }
            }
            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;
            RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
            hv.setup(imageView.getUnRotatedMatrix(), imageRect, cropRect, aspectX != 0 && aspectY != 0);
            imageView.add(hv);
        }

        public void crop() {
            handler.post(new Runnable() {

                public void run() {
                    makeDefault();
                    imageView.invalidate();
                    if (imageView.highlightViews.size() == 1) {
                        cropView = imageView.highlightViews.get(0);
                        cropView.setFocus(true);
                    }
                }
            });
        }

    }

    private void saveImage(Bitmap croppedImage) {
        if (croppedImage != null) {
            final Bitmap b = croppedImage;
            CropUtil.startBackgroundJob(this, null, "正在保存...", new Runnable() {

                public void run() {
                    saveOutput(b);
                }
            }, handler);
        } else {
            finish();
        }
    }

    @TargetApi(10)
    private Bitmap decodeRegionCrop(Bitmap croppedImage, Rect rect) {
        clearImageView();
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(sourceUri);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is,
                    false);
            final int width = decoder.getWidth();
            final int height = decoder.getHeight();

            if (exifRotation != 0) {
                Matrix matrix = new Matrix();
                matrix.setRotate(-exifRotation);
                RectF adjusted = new RectF();
                matrix.mapRect(adjusted, new RectF(rect));
                adjusted.offset(adjusted.left < 0 ? width : 0,
                        adjusted.top < 0 ? height : 0);
                rect = new Rect((int) adjusted.left, (int) adjusted.top,
                        (int) adjusted.right, (int) adjusted.bottom);
            }

            try {
                croppedImage = decoder.decodeRegion(rect,
                        new BitmapFactory.Options());

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Rectangle " + rect
                        + " is outside of the image (" + width + "," + height
                        + "," + exifRotation + ")", e);
            }

        } catch (IOException e) {
            finish();
        } catch (OutOfMemoryError e) {
            setResultException(e);
        } finally {
            CropUtil.closeSilently(is);
        }
        return croppedImage;
    }

    private Bitmap inMemoryCrop(RotateBitmap rotateBitmap, Bitmap croppedImage, Rect r, int width, int height, int outWidth, int outHeight) {
        System.gc();
        try {
            croppedImage = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.RGB_565);
            RectF dstRect = new RectF(0, 0, width, height);
            Matrix m = new Matrix();
            m.setRectToRect(new RectF(r), dstRect, Matrix.ScaleToFit.FILL);
            m.preConcat(rotateBitmap.getRotateMatrix());
            if (isCircleCrop) {
                return cropCircleView(rotateBitmap.getBitmap(), croppedImage, m);
            }
            Canvas canvas = new Canvas(croppedImage);
            canvas.drawBitmap(rotateBitmap.getBitmap(), m, null);
        } catch (OutOfMemoryError e) {
            setResultException(e);
            System.gc();
        }
        clearImageView();
        return croppedImage;
    }

    private void clearImageView() {
        imageView.clear();
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
        System.gc();
    }

    private void saveOutput(Bitmap croppedImage) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {

                    if (exifRotation > 0) {
                        try {
                            Matrix matrix = new Matrix();
                            matrix.reset();
                            matrix.postRotate(exifRotation);
                            Bitmap bMapRotate = Bitmap.createBitmap(
                                    croppedImage, 0, 0,
                                    croppedImage.getWidth(),
                                    croppedImage.getHeight(), matrix, true);
                            bMapRotate.compress(Bitmap.CompressFormat.PNG, 70,
                                    outputStream);

                        } catch (Exception e) {
                            e.printStackTrace();
                            croppedImage.compress(Bitmap.CompressFormat.PNG,
                                    70, outputStream);
                        } finally {
                            if (croppedImage != null
                                    && !croppedImage.isRecycled()) {
                                croppedImage.recycle();
                            }
                        }
                    } else {
                        croppedImage.compress(Bitmap.CompressFormat.PNG, 70,
                                outputStream);
                    }
                }

            } catch (IOException e) {
                setResultException(e);
            } finally {
                CropUtil.closeSilently(outputStream);
            }

            if (!IN_MEMORY_CROP) {
                CropUtil.copyExifRotation(CropUtil.getFromMediaUri(
                        getContentResolver(), sourceUri), CropUtil
                        .getFromMediaUri(getContentResolver(), saveUri));
            }

            setResultUri(saveUri);
        }

        final Bitmap b = croppedImage;
        handler.post(new Runnable() {

            public void run() {
                imageView.clear();
                b.recycle();
            }
        });

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rotateBitmap != null) {
            rotateBitmap.recycle();
        }
    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    public boolean isSaving() {
        return isSaving;
    }

    private void setResultUri(Uri uri) {
        setResult(RESULT_OK, new Intent().putExtra(MediaStore.EXTRA_OUTPUT, uri));
    }

    private void setResultException(Throwable throwable) {
        setResult(Crop.RESULT_ERROR, new Intent().putExtra(Crop.Extra.ERROR, throwable));
    }

    private Bitmap cropCircleView(Bitmap scaledSrcBmp, Bitmap output, Matrix m) {
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, m, paint);
        return output;
    }

    private Bitmap cropCircleView(Bitmap scaledSrcBmp) {
        return GraphicsUtil.getCircleBitmap(scaledSrcBmp);
        // return GraphicsUtil.getOvalBitmap( scaledSrcBmp);
        // return GraphicsUtil.getRoundedShape( scaledSrcBmp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.action_done) {
            onSaveClicked();
        }
        return super.onOptionsItemSelected(item);
    }
}
