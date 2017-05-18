package com.shenhua.nandagy.ui.activity.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.lib.boxing.impl.Boxing;
import com.shenhua.lib.boxing.loader.BoxingGlideLoader;
import com.shenhua.lib.boxing.loader.BoxingMediaLoader;
import com.shenhua.lib.boxing.model.entity.BaseMedia;
import com.shenhua.lib.boxing.ui.BoxingActivity;
import com.shenhua.lib.emoji.EmojiLayout;
import com.shenhua.lib.keyboard.utils.KPSwitchConflictUtil;
import com.shenhua.lib.keyboard.utils.KeyboardUtil;
import com.shenhua.lib.keyboard.widget.KPSwitchPanelLinearLayout;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.PhotosSelectedAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

import static com.shenhua.lib.boxing.utils.Contants.EXTRA_SELECTED_MEDIA;

/**
 * 发布动态界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_publish_dynamic,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_publish_dynamic,
        toolbarTitleId = R.id.toolbar_title
)
public class PublishDynamicActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.et_publish)
    EditText mPublishEt;
    @BindView(R.id.ib_emoji)
    ImageButton mEmojiIb;
    @BindView(R.id.ib_location)
    ImageButton mLocationIb;
    @BindView(R.id.panel_root)
    KPSwitchPanelLinearLayout mPanelRoot;
    @BindView(R.id.sub_panel_emoji)
    EmojiLayout mEmojiPanel;
    @BindView(R.id.sub_panel_lacation)
    View mLocationPanel;
    @BindView(R.id.recyclerView)
    RecyclerView mPhotosRecyclerView;
    public static final int REQUEST_MITILL_SELECTED_PHOTOS = 10;
    public static final int REQUEST_MITILL_PREVIEW_PHOTOS = 11;
    private boolean mCanPublish;
    private PhotosSelectedAdapter photosSelectedAdapter;
    private ArrayList<BaseMedia> medias;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mPublishEt.addTextChangedListener(this);

        initKeyboard();
    }

    private void initKeyboard() {
        KeyboardUtil.attach(this, mPanelRoot);
        KPSwitchConflictUtil.attach(mPanelRoot, mPublishEt, switchToPanel -> {
                    if (switchToPanel) {
                        mPublishEt.clearFocus();
                    } else {
                        mPublishEt.requestFocus();
                    }
                },
                new KPSwitchConflictUtil.SubPanelAndTrigger(mEmojiPanel, mEmojiIb),
                new KPSwitchConflictUtil.SubPanelAndTrigger(mLocationPanel, mLocationIb));
        mEmojiPanel.init(this, mPublishEt, new String[]{"emoji_ay", "emoji_aojiao", "emoji_d"});

        photosSelectedAdapter = new PhotosSelectedAdapter(this, null);
        mPhotosRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mPhotosRecyclerView.setAdapter(photosSelectedAdapter);
        photosSelectedAdapter.setOnItemClickListener((view, position, data)
                -> Boxing.startPreview(PublishDynamicActivity.this, medias, position, REQUEST_MITILL_PREVIEW_PHOTOS));
    }

    private void publish(List<BmobFile> bmobFiles) {
        UserZone userZone = UserZoneUtils.getInstance().getUserZone(this);
        SchoolCircle circle = new SchoolCircle();
        circle.setUserzone(userZone);
        circle.setGreat(0);
        circle.setHate(0);
        circle.setComment(0);
        circle.setContent(mPublishEt.getText().toString());
        if (bmobFiles != null) {
            circle.setPics(bmobFiles);
        }
        circle.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("发布成功");
                    setResult(RESULT_OK);
                    PublishDynamicActivity.this.finish();
                } else {
                    toast("发布失败:" + e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.ib_camera)
    void selectPhotos() {
        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader());
        Boxing.buildMultiImagesChoiceWithCamera().withIntent(this, BoxingActivity.class, medias).start(this, REQUEST_MITILL_SELECTED_PHOTOS);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_menu_send).setEnabled(mCanPublish);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_send) {
            hideKeyboard();
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
                    /**
                     * 批量上传成功
                     * @param list 上传完成后的BmobFile集合，是为了方便对其上传后的数据进行操作，例如你可以将该文件保存到表中
                     * @param list1 上传文件的完整url地址
                     */
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if (list1.size() == files.length) {// 如果数量相等，则代表文件全部上传完成
                            Log.d("shenhuaLog -- " + PublishDynamicActivity.class.getSimpleName(), "onSuccess: 等");
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            publish(list);
                        } else {
                            Log.d("shenhuaLog -- " + PublishDynamicActivity.class.getSimpleName(), "onSuccess: 不等");
                        }
                    }

                    /**
                     * 上传进度
                     * @param i 表示当前第几个文件正在上传
                     * @param i1 表示当前上传文件的进度值（百分比）
                     * @param i2 表示总的上传文件数
                     * @param i3 表示总的上传进度（百分比）
                     */
                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                        dialog.setProgress(i);
                    }

                    @Override
                    public void onError(int i, String s) {
                        toast("上传失败:" + s);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

            } else {
                publish(null);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mCanPublish = mPublishEt.getText().length() > 0;
        invalidateOptionsMenu();
    }

    @OnClick({R.id.layout_edit_root, R.id.recyclerView})
    void onOutTouch() {
        hideKeyboard();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPanelRoot.getVisibility() == View.VISIBLE) {
            KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPhotosRecyclerView.setVisibility(View.VISIBLE);
            if (requestCode == REQUEST_MITILL_SELECTED_PHOTOS) {
                this.medias = Boxing.getResult(data);
            }
            if (requestCode == REQUEST_MITILL_PREVIEW_PHOTOS) {
                this.medias = data.getParcelableArrayListExtra(EXTRA_SELECTED_MEDIA);
            }
            if (medias == null || medias.size() == 0) {
                mPhotosRecyclerView.setVisibility(View.GONE);
            }
            photosSelectedAdapter.setDatas(medias);
        }
    }
}
