package com.shenhua.nandagy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shenhua.nandagy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 带验证码的成绩查询基类
 * Created by shenhua on 9/8/2016.
 */
public abstract class BaseScoreQueryVerfyFragment extends BaseScoreQueryFragment {

    @BindView(R.id.iv_score_yzm)
    ImageView mVerifyCodeIv;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showVerifyCode();
    }

    public void showVerifyCode() {
        Glide.with(this).load(getVerifyCodeUrl())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_score_yzm)
                .error(R.drawable.ic_score_yzm)
                .fitCenter()
                .into(mVerifyCodeIv);
    }

    @OnClick(R.id.iv_score_yzm)
    void clicks() {
        showVerifyCode();
    }

    public abstract String getVerifyCodeUrl();

}
