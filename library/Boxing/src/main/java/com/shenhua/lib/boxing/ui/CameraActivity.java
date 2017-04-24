package com.shenhua.lib.boxing.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shenhua.lib.boxing.utils.BoxingFileHelper;
import com.shenhua.lib.boxing.utils.BoxingLog;
import com.shenhua.lib.boxing.utils.Contants;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.functions.Action1;

/**
 * Created by shenhua on 4/18/2017.
 * Email shenhuanet@126.com
 */
public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 2;
    private Uri mSourceUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxPermissions.getInstance(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            takePhoto();
                        } else {
                            Toast.makeText(CameraActivity.this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            final String cameraOutDir = BoxingFileHelper.getExternalDCIM(Contants.DEFAULT_SUB_DIR);
            try {
                if (BoxingFileHelper.createFile(cameraOutDir)) {
                    File mOutputFile = new File(cameraOutDir, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mSourceUri = FileProvider.getUriForFile(this, getPackageName() + ".file.provider", mOutputFile);
                    } else {
                        mSourceUri = Uri.fromFile(mOutputFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mSourceUri);
                    try {
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    } catch (ActivityNotFoundException ignore) {
                        ignore.printStackTrace();
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                BoxingLog.d("create file" + cameraOutDir + " error.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Uri destUri = new Uri.Builder().scheme("file").appendPath(this.getCacheDir().getPath())
                    .appendPath(String.valueOf(System.currentTimeMillis()) + ".jpg").build();
            UCrop.of(mSourceUri, destUri).withAspectRatio(1f, 1f).withMaxResultSize(400, 400).start(this);
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            // TODO: 4/18/2017 没有设置进去
            setIntent(new Intent().setData(resultUri));
            setResult(RESULT_OK);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            finish();
        }
    }

}
