package com.shenhua.nandagy.ui.activity.me;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.NewMessageAdapter;
import com.shenhua.nandagy.bean.NewMessageData;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.ui.fragment.more.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息中心界面
 * Created by Shenhua on 9/4/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_message,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_message,
        toolbarTitleId = R.id.toolbar_title
)
public class MessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipRefereshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<NewMessageData> datas;

    @Override
    protected void initView(BaseActivity baseActivity) {
        ButterKnife.bind(this);
        mSwipRefereshLayout.setColorSchemeColors(Color.parseColor("#1DBFD8"));
        mSwipRefereshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new NewMessageAdapter(this, getDatas()));
        BusProvider.getInstance().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_MESSAGE));
    }

    private List<NewMessageData> getDatas() {
        datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            NewMessageData data = new NewMessageData();
            data.setTitle("标题" + Integer.toString(i));
            data.setContent("我是评论或点赞的正文内容" + Integer.toString(i));
            data.setFrom("来自 " + "北风");
            data.setTime(Long.toString(System.currentTimeMillis()));
            datas.add(data);
        }
        return datas;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> mSwipRefereshLayout.setRefreshing(false), 3000);
    }
}
