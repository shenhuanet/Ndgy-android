package com.shenhua.nandagy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.NewMessageAdapter;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.bean.NewMessageData;
import com.shenhua.nandagy.callback.NewMessageEventBus;
import com.shenhua.nandagy.frag.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 消息中心界面
 * Created by Shenhua on 9/4/2016.
 */
public class MessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipRefereshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<NewMessageData> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setupActionBar("消息中心", true);

        mSwipRefereshLayout.setColorSchemeColors(Color.parseColor("#1DBFD8"));
        mSwipRefereshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new NewMessageAdapter(this, getDatas()));
        EventBus.getDefault().post(new NewMessageEventBus(true, UserFragment.EVENT_TYPE_MESSAGE));
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipRefereshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
