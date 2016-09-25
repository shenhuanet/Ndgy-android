package com.shenhua.photopicker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.shenhua.photopicker.activity.CropImageActivity;

import java.io.File;

public class Crop {

    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int REQUEST_TAKE = 9163;
    public static final int RESULT_ERROR = 404;
    private Intent cropIntent;
    private File mTempDir;

    public interface Extra {
        String ASPECT_X = "aspect_x";
        String ASPECT_Y = "aspect_y";
        String MAX_X = "max_x";
        String MAX_Y = "max_y";
        String ERROR = "error";
        String IS_CIRCLE_CROP = "is_circle_crop";
    }

    public Crop(Uri source) {
        cropIntent = new Intent();
        cropIntent.setData(source);
    }

    public Crop fileOutDir(String dir) {
        mTempDir = new File(Environment.getExternalStorageDirectory(), dir);
        if (!mTempDir.exists()) mTempDir.mkdirs();
        return this;
    }

    public Crop fileOutName(String fileName) {
        File cropFile = new File(mTempDir, fileName);
        Uri output = Uri.fromFile(cropFile);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        return this;
    }

    public Crop setCropType(boolean isCircle) {
        cropIntent.putExtra(Extra.IS_CIRCLE_CROP, isCircle);
        if (isCircle) {
            asSquare();
        }
        return this;
    }

    public Crop withAspect(int x, int y) {
        cropIntent.putExtra(Extra.ASPECT_X, x);
        cropIntent.putExtra(Extra.ASPECT_Y, y);
        return this;
    }

    public Crop withMaxSize(int width, int height) {
        cropIntent.putExtra(Extra.MAX_X, width);
        cropIntent.putExtra(Extra.MAX_Y, height);
        return this;
    }

    public Crop asSquare() {
        cropIntent.putExtra(Extra.ASPECT_X, 1);
        cropIntent.putExtra(Extra.ASPECT_Y, 1);
        return this;
    }

    public void start(Activity activity) {
        activity.startActivityForResult(getIntent(activity), REQUEST_CROP);
    }

    public void start(Context context, Fragment fragment) {
        fragment.startActivityForResult(getIntent(context), REQUEST_CROP);
    }

    @VisibleForTesting
    Intent getIntent(Context context) {
        cropIntent.setClass(context, CropImageActivity.class);
        return cropIntent;
    }

    public static Uri getOutput(Intent result) {
        return result.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
    }

    public static Throwable getError(Intent result) {
        return (Throwable) result.getSerializableExtra(Extra.ERROR);
    }

    /**
     * 从相册选择
     *
     * @param activity activity
     */
    public static void pickImage(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try {
            activity.startActivityForResult(intent, REQUEST_PICK);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "发生错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从相册选择
     *
     * @param fragment fragment
     */
    public static void pickImage(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        try {
            fragment.startActivityForResult(intent, REQUEST_PICK);
        } catch (Exception e) {
            Toast.makeText(fragment.getActivity(), "发生错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拍照
     *
     * @param activity activity
     */
    public static String takePhoto(Activity activity, String dir, String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = crateFileDir(dir, fileName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        String finalPath = fileUri.getPath();
        try {
            activity.startActivityForResult(intent, REQUEST_TAKE);
        } catch (Exception e) {
            Toast.makeText(activity, "发生错误！", Toast.LENGTH_SHORT).show();
            return null;
        }
        return finalPath;
    }

    /**
     * 拍照
     *
     * @param fragment fragment
     */
    public String takePhoto(Fragment fragment, String dir, String fileName) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = crateFileDir(dir, fileName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        String finalPath = fileUri.getPath();
        try {
            fragment.getActivity().startActivityForResult(intent, REQUEST_TAKE);
        } catch (Exception e) {
            Toast.makeText(fragment.getActivity(), "发生错误！", Toast.LENGTH_SHORT).show();
            return null;
        }
        return finalPath;
    }

    private static Uri crateFileDir(String dir, String fileName) {
        File mTempDir = new File(Environment.getExternalStorageDirectory(), dir);
        if (!mTempDir.exists()) mTempDir.mkdirs();
        File cropFile = new File(mTempDir, fileName);
        return Uri.fromFile(cropFile);
    }
}
