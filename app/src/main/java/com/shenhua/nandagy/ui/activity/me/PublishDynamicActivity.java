package com.shenhua.nandagy.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shenhua.lib.boxing.utils.Contants.EXTRA_SELECTED_MEDIA;
import static com.shenhua.nandagy.R.id.sub_panel_emoji;

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
    @BindView(R.id.layout_edit_root)
    LinearLayout mEditRootLayout;
    @BindView(sub_panel_emoji)
    EmojiLayout mEmojiPanel;
    @BindView(R.id.sub_panel_lacation)
    View mLocationPanel;
    @BindView(R.id.recyclerView)
    RecyclerView mPhotosRecyclerView;
    private PhotosSelectedAdapter photosSelectedAdapter;
    private boolean mCanPublish;
    private ArrayList<BaseMedia> medias;
    public static final int REQUEST_MITILL_SELECTED_PHOTOS = 10;
    public static final int REQUEST_MITILL_PREVIEW_PHOTOS = 11;
    private String[] mEmojiDirs = {"emoji_ay", "emoji_aojiao", "emoji_d"};

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mPublishEt.addTextChangedListener(this);

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

        photosSelectedAdapter = new PhotosSelectedAdapter(this, null);
        mPhotosRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mPhotosRecyclerView.setAdapter(photosSelectedAdapter);
        photosSelectedAdapter.setOnItemClickListener((view, position, data)
                -> Boxing.startPreview(PublishDynamicActivity.this, medias, position, REQUEST_MITILL_PREVIEW_PHOTOS));
        mEmojiPanel.init(this, mPublishEt, mEmojiDirs);
//        mEmojiPanel.init(this, mPublishEt);
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
            // TODO sth.
            hideKeyboard();
            toast(mPublishEt.getText().toString());
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
