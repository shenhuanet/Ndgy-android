package com.shenhua.nandagy.ui.activity.me;

import android.text.TextUtils;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.ConvertUtils;
import com.shenhua.commonlibs.widget.WordLimitEditText;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.FeedBack;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 反馈意见页面
 * Created by shenhua on 2016/5/3.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_feedback,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_feedback
)
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.edit_txt_feedback_content)
    WordLimitEditText mContentEditTv;

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
        setToolbarTitle(R.id.toolbar_title);
        mContentEditTv.setFrameLayoutHeight(ConvertUtils.dp2px(this, 150));
        mContentEditTv.setMaxLengh(80);
    }

    @OnClick(R.id.btn_commit)
    void commitFeedback() {
        hideKeyboard();
        String content = mContentEditTv.getText();
        if (TextUtils.isEmpty(content)) {
            toast("请先写点东西呗...");
            return;
        }
        LoadingAlertDialog.showLoadDialog(this, "意见提交中...", true);
        mContentEditTv.postDelayed(() -> {
            FeedBack feedBack = new FeedBack();
            feedBack.setOption(1);
            feedBack.setContent(mContentEditTv.getText());
            feedBack.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    LoadingAlertDialog.dissmissLoadDialog();
                    if (e == null) toast("操作成功，感谢你的反馈，我们以后会做得更好！");
                    else toast("操作失败：" + e.getMessage());
                }
            });
        }, 1000);
    }
}
