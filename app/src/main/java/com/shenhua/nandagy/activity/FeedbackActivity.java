package com.shenhua.nandagy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MenuItem;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.bmobbean.FeedBack;
import com.shenhua.nandagy.utils.MeasureUtil;
import com.shenhua.nandagy.widget.LoadingAlertDialog;
import com.shenhua.nandagy.widget.WordLimitEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 反馈意见页面
 * Created by shenhua on 2016/5/3.
 */
public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.edit_txt_feedback_content)
    WordLimitEditText mContentEditTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        setupActionBar("反馈意见", true);
        mContentEditTv.setFrameLayoutHeight(MeasureUtil.dip2px(this, 150));
        mContentEditTv.setMaxLengh(80);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_commit)
    void commitFeedback() {
        hideKbTwo();
        String content = mContentEditTv.getText();
        if (TextUtils.isEmpty(content)) {
            toast("请先写点东西呗...");
            return;
        }
        LoadingAlertDialog.showLoadDialog(this, "意见提交中...", true);
        mContentEditTv.postDelayed(new Runnable() {
            @Override
            public void run() {
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
            }
        }, 1000);
    }
}
