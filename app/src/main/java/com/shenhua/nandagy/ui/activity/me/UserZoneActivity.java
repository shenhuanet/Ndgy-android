package com.shenhua.nandagy.ui.activity.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.widget.BaseShareView;
import com.shenhua.commonlibs.widget.CircleImageView;
import com.shenhua.lib.boxing.impl.Boxing;
import com.shenhua.lib.boxing.impl.BoxingCrop;
import com.shenhua.lib.boxing.impl.BoxingUcrop;
import com.shenhua.lib.boxing.loader.BoxingGlideLoader;
import com.shenhua.lib.boxing.loader.BoxingMediaLoader;
import com.shenhua.lib.boxing.model.entity.BaseMedia;
import com.shenhua.lib.boxing.ui.BoxingActivity;
import com.shenhua.lib.boxing.ui.CameraActivity;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.databinding.ActivityUserZoneBinding;
import com.shenhua.nandagy.ui.activity.ImageViewerActivity;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;
import com.shenhua.nandagy.widget.LoadingAlertDialog;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.shenhua.nandagy.R.string.toolbar_title_user_zone_my;

/**
 * 用户主页，用于自己访问自己
 * Created by Shenhua on 9/3/2016.
 */
@ActivityFragmentInject(
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = toolbar_title_user_zone_my,
        toolbarTitleId = R.id.toolbar_title
)
public class UserZoneActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_zone_photo)
    CircleImageView mCircleImageView;
    @BindView(R.id.tv_zone_id)
    TextView mZoneIdTv;
    @BindView(R.id.tv_zone_exper)
    TextView mZoneExperTv;
    @BindView(R.id.tv_zone_mi)
    TextView mZoneMiTv;
    @BindView(R.id.bpv)
    BaseShareView mBpv;
    public static final int REQUEST_EDIT = 1;
    private static final int REQUEST_TAKE = 12;
    private static final int REQUEST_SELECT = 13;
    private boolean accessFromMe;
    private ActivityUserZoneBinding binding;
    private UserZone userZoneBean;
    private String zoneObjectId;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_zone);
        initToolbar();
        ButterKnife.bind(this);
        accessFromMe = getIntent().getBooleanExtra("isMyself", false);
        mAppBarLayout.addOnOffsetChangedListener(this);
        initSelectPhotoView();

        updataViews(accessFromMe);
    }

    /**
     * 更新界面
     *
     * @param upgrade 是否从网络读取
     */
    private void updataViews(boolean upgrade) {
        zoneObjectId = getIntent().getStringExtra("zoneObjectId");
        setupActionbarTitle(accessFromMe ? R.string.toolbar_title_user_zone_my : R.string.toolbar_title_user_zone);
        if (!upgrade) {
            BmobQuery<UserZone> query = new BmobQuery<>();
            query.include("user");
            query.getObject(zoneObjectId, new QueryListener<UserZone>() {
                @Override
                public void done(UserZone userZone, BmobException e) {
                    if (e == null) {
                        binding.setUserZone(userZone);
                        userZoneBean = userZone;
                        AvatarUtils.loadUserAvatar(UserZoneActivity.this, userZone.getUser(), mCircleImageView);
                        if (accessFromMe) {
                            mZoneIdTv.setText(String.format(getString(R.string.userzone_text_objid), zoneObjectId));
                            UserZoneUtils.getInstance().saveUserZone(UserZoneActivity.this, userZone);
                            return;
                        }
                        mZoneIdTv.setText(TextUtils.isEmpty(userZone.getUser().getNick()) ?
                                String.format(getString(R.string.userzone_text_objid), zoneObjectId) :
                                userZone.getUser().getNick());
                    } else {
                        toast("用户主页资料获取失败：" + e.getMessage());
                    }
                }
            });
        } else {// 自己访问，不要从网络请求
            UserZone uz = UserZoneUtils.getInstance().getUserZone(this);
            MyUser user = BmobUser.getCurrentUser(MyUser.class);
            userZoneBean = uz;
            binding.setUserZone(uz);
            AvatarUtils.loadUserAvatar(UserZoneActivity.this, user, mCircleImageView);
            mZoneIdTv.setText(String.format(getString(R.string.userzone_text_objid), zoneObjectId));
        }
    }

    /**
     * 初始化头像点击事件
     */
    private void initSelectPhotoView() {
        if (!accessFromMe) return;
        BoxingMediaLoader.getInstance().init(new BoxingGlideLoader());
        BoxingCrop.getInstance().init(new BoxingUcrop());
        mBpv.setInterpolator(new BounceInterpolator());
        View content = mBpv.getContentView();
        TextView take = (TextView) content.findViewById(R.id.tv_take_photo);
        take.setOnClickListener(v -> {
            mBpv.hide();
            Boxing.of().takePicture(this, CameraActivity.class, REQUEST_TAKE);

        });
        TextView pick = (TextView) content.findViewById(R.id.tv_pick_photo);
        pick.setOnClickListener(v -> {
            mBpv.hide();
            String cachePath = Environment.getExternalStorageDirectory() + File.separator + "Pictures";
            if (TextUtils.isEmpty(cachePath)) {
                Toast.makeText(getApplicationContext(), "设备存储读取出错或暂不可用，请稍候重试", Toast.LENGTH_SHORT).show();
                return;
            }
            MyUser user = BmobUser.getCurrentUser(MyUser.class);
            Boxing.buildSingleImageChoiceWithCorp(this, user.getObjectId())
                    .withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT);
        });
        TextView cancel = (TextView) content.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(v -> mBpv.hide());
    }

    @OnClick(R.id.iv_zone_photo)
    void photo() {
        if (accessFromMe && !mBpv.getIsShowing()) {
            mBpv.show();
        } else {
            ArrayList<String> imgs = new ArrayList<>();
            // TODO: 5/9/2017 男女头像详情查看,考虑使用imageView getTag()
            imgs.add(AvatarUtils.getOtherUserAvatarUrl(userZoneBean));
            startActivity(new Intent(this, ImageViewerActivity.class).putStringArrayListExtra(ImageViewerActivity.EXTRA_IMGS_KEY, imgs));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_menu_edit).setVisible(accessFromMe);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zone_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_edit) {
            if (userZoneBean == null) {
                toast("当前无法编辑个人信息，请稍候重试");
                return true;
            }
            Intent intent = new Intent(this, UserZoneEditActivity.class);
            intent.putExtra("zoneObjId", zoneObjectId);
            startActivityForResult(intent, REQUEST_EDIT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mBpv.getIsShowing()) {
                mBpv.hide();
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDIT:
                    updataViews(true);
                    break;
                case REQUEST_TAKE:
                    if (data != null) {
                        upLoadPhoto(data.getData().getPath());
                    } else {
                        toast("照片选择发生错误");
                    }
                    break;
                case REQUEST_SELECT:
                    ArrayList<BaseMedia> medias = Boxing.getResult(data);
                    if (medias != null && medias.size() > 0) {
                        upLoadPhoto(medias.get(0).getPath());
                    } else {
                        toast("照片选择发生错误");
                    }
                    break;
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            toast("操作失败");
        }
    }

    /**
     * 上传头像
     *
     * @param filePath filePath
     */
    private void upLoadPhoto(String filePath) {
        LoadingAlertDialog.getInstance(this).showLoadDialog("头像更新中...", true);
        BmobFile bmobFile = new BmobFile(new File(filePath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                LoadingAlertDialog.getInstance(UserZoneActivity.this).dissmissLoadDialog();
                if (e == null) {
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    user.setAvatar(bmobFile);
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                toast("头像更新成功！");
                            } else {
                                toast("头像更新失败：" + e.getMessage());
                            }
                        }
                    });
                    UserUtils.getInstance().updateUserInfo(UserZoneActivity.this, "url_photo", bmobFile.getFileUrl());
                    Glide.with(UserZoneActivity.this).load(bmobFile.getFileUrl()).centerCrop().into(mCircleImageView);
                } else {
                    toast("头像更新失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mToolbarTitle.setAlpha((float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange());
    }

}
