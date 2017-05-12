package com.shenhua.nandagy.ui.activity.more;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.CircleGridAdapter;
import com.shenhua.nandagy.bean.DaoMaster;
import com.shenhua.nandagy.bean.GreatHateFavDao;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.utils.RelativeDateFormat;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;
import com.shenhua.nandagy.utils.bmobutils.CircleDataLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;

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

    public static final String EXTRAKEY = "circle";
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

    @Override
    protected void onCreate(BaseActivity baseActivity, Bundle bundle) {
        ButterKnife.bind(this);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "circle.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        GreatHateFavDao dao = daoMaster.newSession().getGreatHateFavDao();

        SchoolCircle data = (SchoolCircle) getIntent().getSerializableExtra(EXTRAKEY);
        // 用户名
        AvatarUtils.loadUserNick(data.getUserzone(), mUserNickTv);
        // 时间
        mTimeAgoTv.setText(RelativeDateFormat.friendly_time(data.getCreatedAt()));
        // 头像
        AvatarUtils.loadUserAvatar(this, data.getUserzone().getUser(), mUserPhotoIv);
        // 图片组
        List<BmobFile> file = data.getPics();
        if (file != null && file.size() > 0) {
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

    @OnClick({R.id.f_fav, R.id.f_comment, R.id.f_hate, R.id.f_great})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.f_fav:

                break;
            case R.id.f_comment:

                break;
            case R.id.f_hate:

                break;
            case R.id.f_great:

                break;
        }
    }
}
