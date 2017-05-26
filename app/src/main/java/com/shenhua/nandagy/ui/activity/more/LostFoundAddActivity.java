package com.shenhua.nandagy.ui.activity.more;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.CheckUtils;
import com.shenhua.lib.boxing.impl.Boxing;
import com.shenhua.lib.boxing.loader.BoxingGlideLoader;
import com.shenhua.lib.boxing.loader.BoxingMediaLoader;
import com.shenhua.lib.boxing.model.entity.BaseMedia;
import com.shenhua.lib.boxing.ui.BoxingActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.LostAndFound;
import com.shenhua.nandagy.bean.bmobbean.MyUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 添加失物招领信息
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_lost_found_add,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_lost_add,
        toolbarTitleId = R.id.toolbar_title
)
public class LostFoundAddActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText mTitleEt;
    @BindView(R.id.et_desc)
    EditText mDescEt;
    @BindView(R.id.et_phone)
    EditText mPhoneEt;
    @BindView(R.id.sw_type)
    Switch mTypeSwith;
    @BindView(R.id.btn_add_image)
    Button mAddImageBtn;
    @BindView(R.id.btn_done)
    Button mDoneBtn;

    private int mDefaultType = LostAndFound.TYPE_LOST;
    private ArrayList<BaseMedia> medias;
    private static final int REQUEST_SINGLE_SELECTED_PHOTO = 1;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle bundle) {
        ButterKnife.bind(this);
        mTypeSwith.setOnCheckedChangeListener((buttonView, isChecked) ->
                mDefaultType = isChecked ? LostAndFound.TYPE_LOST : LostAndFound.TYPE_FOUND);
    }

    @OnClick(R.id.btn_done)
    void done() {
        hideKeyboard();
        String title = mTitleEt.getText().toString();
        String desc = mDescEt.getText().toString();
        String phone = mPhoneEt.getText().toString();
        if (isEmpty(title, desc, phone)) {
            toast("请完善相关信息");
            return;
        }
        if (!CheckUtils.isPhone(phone)) {
            toast("手机号码填写有误");
            return;
        }
        if (medias != null && medias.size() > 0) {
            uploadImage();
            return;
        }
        publish(null);
    }

    @OnClick(R.id.btn_add_image)
    void choiceImage() {
        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader());
        Boxing.buildSingleImageChoice().withIntent(this, BoxingActivity.class, medias).start(this, REQUEST_SINGLE_SELECTED_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SINGLE_SELECTED_PHOTO) {
                if (medias == null) {
                    medias = new ArrayList<>();
                }
                medias.clear();
                this.medias = Boxing.getResult(data);
                mAddImageBtn.setText("已选择一张图片");
            }
        }
    }

    // 先上传一张图片
    private void uploadImage() {
        if (medias != null && medias.size() > 0) {
            String[] files = new String[medias.size()];
            for (int i = 0; i < medias.size(); i++) {
                files[i] = medias.get(i).getPath();
            }
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(medias.size());
            dialog.setCancelable(false);
            dialog.setMessage("正在上传图片");
            dialog.show();
            dialog.setProgress(0);
            BmobFile.uploadBatch(files, new UploadBatchListener() {

                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if (list1.size() == files.length) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        publish(list);
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                    dialog.setProgress(i);
                }

                @Override
                public void onError(int i, String s) {
                    toast("图片上传失败:" + s);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            publish(null);
        }
    }

    private void publish(List<BmobFile> pics) {
        mDoneBtn.setEnabled(false);
        String title = mTitleEt.getText().toString();
        String desc = mDescEt.getText().toString();
        String phone = mPhoneEt.getText().toString();
        LostAndFound lostAndFound = new LostAndFound();
        lostAndFound.setUser(BmobUser.getCurrentUser(MyUser.class));
        lostAndFound.setTitle(title);
        lostAndFound.setType(mDefaultType);
        lostAndFound.setDescribe(desc);
        lostAndFound.setContact(phone);
        lostAndFound.setIsResolved(false);
        if (pics != null) {
            lostAndFound.setPics(pics);
        }
        lostAndFound.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                mDoneBtn.setEnabled(true);
                if (e == null) {
                    toast("发布成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    toast("发布失败");
                }
            }
        });
    }

    private static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (!TextUtils.isEmpty(string)) {
                return false;
            }
        }
        return true;
    }
}
