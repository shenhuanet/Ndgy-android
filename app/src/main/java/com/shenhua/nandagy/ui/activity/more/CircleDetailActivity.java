package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.lib.emoji.EmojiLayout;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.lib.keyboard.utils.KPSwitchConflictUtil;
import com.shenhua.lib.keyboard.utils.KeyboardUtil;
import com.shenhua.lib.keyboard.widget.KPSwitchPanelLinearLayout;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleDetailCmAdapter;
import com.shenhua.nandagy.adapter.CircleDetailUzAdapter;
import com.shenhua.nandagy.adapter.CircleGridAdapter;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircleComment;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.database.DaoMaster;
import com.shenhua.nandagy.database.GreatHateFavDao;
import com.shenhua.nandagy.utils.RelativeDateFormat;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;
import com.shenhua.nandagy.utils.bmobutils.CircleDataLoader;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by shenhua on 5/12/2017.
 * Email shenhuanet@126.com
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_circle_detail,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_more_circle,
        toolbarTitleId = R.id.toolbar_title
)
public class CircleDetailActivity extends BaseActivity {

    @BindView(R.id.tv_user_nick)
    TextView mUserNickTv;
    @BindView(R.id.tv_time_ago)
    TextView mTimeAgoTv;
    @BindView(R.id.iv_user_photo)
    ImageView mUserPhotoIv;
    @BindView(R.id.tv_content)
    TextView mContentTv;
    @BindView(R.id.gridView)
    GridView mPhotosGv;
    @BindView(R.id.tv_fav)
    TextView mFavTv;
    @BindView(R.id.tv_comment)
    TextView mCommentTv;
    @BindView(R.id.tv_hate)
    TextView mHateTv;
    @BindView(R.id.tv_great)
    TextView mGreatTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyvlerView;

    @BindView(R.id.panel_root)
    KPSwitchPanelLinearLayout mPanelRoot;
    @BindView(R.id.et_comment)
    EditText mCommentEt;
    @BindView(R.id.sub_panel_emoji)
    EmojiLayout mEmojiPanel;
    @BindView(R.id.ib_emoji)
    ImageButton mEmojiIb;
    @BindView(R.id.ib_send)
    ImageButton mSendIb;

    @BindView(R.id.progress_bar)
    ProgressBar mProgerssBar;
    @BindView(R.id.emptey)
    TextView mEmptyView;
    public static final String EXTRAKEY = "circle";
    private static final int M_FAV = 0;
    private static final int M_COMMENT = 1;
    private static final int M_HATE = 2;
    private static final int M_GRTAT = 3;
    private SchoolCircle data;
    private int mCurrent = -1;

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle bundle) {
        ButterKnife.bind(this);

        initKeyboard();
        initData();
        initCommentData();
        mRecyvlerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSendIb.setEnabled(mCommentEt.getText().length() > 0);
            }
        });
    }

    private void onDataLoding(boolean b) {
        mProgerssBar.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    private void onDataEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyvlerView.setVisibility(View.GONE);
    }

    public void onUzDataLoaded(List<UserZone> list, BmobException e) {
        onDataLoding(false);
        if (e == null) {
            if (list.size() > 0) {
                mEmptyView.setVisibility(View.INVISIBLE);
                mRecyvlerView.setVisibility(View.VISIBLE);
                CircleDetailUzAdapter adapter = new CircleDetailUzAdapter(this, list);
                mRecyvlerView.setAdapter(adapter);
            } else {
                onDataEmpty();
            }
        } else {
            onDataEmpty();
        }
    }

    /**
     * 获取评论数据
     */
    private void initCommentData() {
        if (mCurrent == M_COMMENT) {
            return;
        }
        onDataLoding(true);
        BmobQuery<SchoolCircleComment> query = new BmobQuery<>();
        query.include("commenter.user");
        query.order("-createdAt");
        query.addWhereEqualTo("commentCircle", new BmobPointer(data));
        query.findObjects(new FindListener<SchoolCircleComment>() {
            @Override
            public void done(List<SchoolCircleComment> list, BmobException e) {
                onDataLoding(false);
                if (e == null) {
                    if (list.size() > 0) {
                        mEmptyView.setVisibility(View.INVISIBLE);
                        mRecyvlerView.setVisibility(View.VISIBLE);
                        CircleDetailCmAdapter adapter = new CircleDetailCmAdapter(CircleDetailActivity.this, list);
                        mRecyvlerView.setAdapter(adapter);
                    } else {
                        onDataEmpty();
                    }
                } else {
                    onDataEmpty();
                }
            }
        });
        mCurrent = M_COMMENT;
    }

    /**
     * 获取点踩数据
     */
    private void initHateData() {
        if (mCurrent == M_HATE) {
            return;
        }
        onDataLoding(true);
        BmobQuery<UserZone> query = new BmobQuery<>();
        query.include("user");
        query.addWhereRelatedTo("hates", new BmobPointer(data));
        query.findObjects(new FindListener<UserZone>() {
            @Override
            public void done(List<UserZone> list, BmobException e) {
                onUzDataLoaded(list, e);
            }
        });
        mCurrent = M_HATE;
    }

    /**
     * 获取收藏数据
     */
    private void initFavData() {
        if (mCurrent == M_FAV) {
            return;
        }
        onDataLoding(true);
        BmobQuery<UserZone> query = new BmobQuery<>();
        query.include("user");
        query.addWhereRelatedTo("favorite", new BmobPointer(data));
        query.findObjects(new FindListener<UserZone>() {
            @Override
            public void done(List<UserZone> list, BmobException e) {
                onUzDataLoaded(list, e);
            }
        });
        mCurrent = M_FAV;
    }

    /**
     * 获取点赞数据
     */
    private void initGreatData() {
        if (mCurrent == M_GRTAT) {
            return;
        }
        onDataLoding(true);
        BmobQuery<UserZone> query = new BmobQuery<>();
        query.include("user");
        query.addWhereRelatedTo("likes", new BmobPointer(data));
        query.findObjects(new FindListener<UserZone>() {
            @Override
            public void done(List<UserZone> list, BmobException e) {
                onUzDataLoaded(list, e);
            }
        });
        mCurrent = M_GRTAT;
    }

    /**
     * 初始化键盘
     */
    private void initKeyboard() {
        KeyboardUtil.attach(this, mPanelRoot);
        KPSwitchConflictUtil.attach(mPanelRoot, mEmojiIb, mCommentEt, switchToPanel -> {
            if (switchToPanel) {
                mCommentEt.clearFocus();
            } else {
                mCommentEt.requestFocus();
            }
        });
        mEmojiPanel.init(this, mCommentEt, new String[]{"emoji_ay", "emoji_aojiao", "emoji_d"});
    }

    /**
     * 设置数据
     */
    private void initData() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "circle.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        GreatHateFavDao dao = daoMaster.newSession().getGreatHateFavDao();

        data = (SchoolCircle) getIntent().getSerializableExtra(EXTRAKEY);
        // 用户名
        AvatarUtils.loadUserNick(data.getUserzone(), mUserNickTv);
        // 时间
        mTimeAgoTv.setText(RelativeDateFormat.friendly_time(data.getCreatedAt()));
        // 头像
        AvatarUtils.loadUserAvatar(this, data.getUserzone().getUser(), mUserPhotoIv);
        // 图片组
        List<BmobFile> file = data.getPics();
        if (file != null && file.size() > 0) {
            mPhotosGv.setVisibility(View.VISIBLE);
            CircleGridAdapter adapter = new CircleGridAdapter(this, file);
            mPhotosGv.setAdapter(adapter);
        }
        // 内容
        mContentTv.setText(data.getContent());
        EmojiLoader.replaceEmoji(this, mContentTv);
        // 底部数据
        mFavTv.setSelected(CircleDataLoader.getInstance().isFav(this, data.getObjectId(), dao));
        mCommentTv.setText(CircleDataLoader.formatNumber(data.getComment()));
        mHateTv.setText(CircleDataLoader.formatNumber(data.getHate()));
        mGreatTv.setText(CircleDataLoader.formatNumber(data.getGreat()));
    }

    @OnClick(R.id.ib_send)
    void comment() {
        if (!UserUtils.getInstance().isLogin()) {
            toast("请登录后操作");
            return;
        }
        if (!UserUtils.getInstance().isCreateZone()) {
            toast("还没有创建空间哦");
            return;
        }
        if (mCommentEt.getText().length() <= 0) {
            toast("写点什么吧");
            return;
        }

        UserZone userZone = UserZoneUtils.getInstance().getUserZone(this);
        SchoolCircleComment comment = new SchoolCircleComment();
        comment.setContent(mCommentEt.getText().toString());
        comment.setCommenter(userZone);
        comment.setCommentCircle(data);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("评论成功");
                    mCommentEt.setText("");
                    KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
                    initCommentData();
                } else {
                    toast("评论失败 " + e.getMessage());
                }
            }
        });
    }

    @OnClick({R.id.f_fav, R.id.f_comment, R.id.f_hate, R.id.f_great})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.f_fav:
                initFavData();
                break;
            case R.id.f_comment:
                initCommentData();
                break;
            case R.id.f_hate:
                initHateData();
                break;
            case R.id.f_great:
                initGreatData();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPanelRoot.getVisibility() == View.VISIBLE) {
            KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
