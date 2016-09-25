package com.shenhua.nandagy.frag;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.activity.ContentDetailActivity;
import com.shenhua.nandagy.activity.WebActivity;
import com.shenhua.nandagy.adapter.XueGongDataAdapter;
import com.shenhua.nandagy.base.BaseFragment;
import com.shenhua.nandagy.bean.XueGongData;
import com.shenhua.nandagy.callback.OnItemClickListener;
import com.shenhua.nandagy.callback.ProgressEventBus;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.presenter.XueGongPresenter;
import com.shenhua.nandagy.view.XueGongView;
import com.shenhua.nandagy.widget.BannerView;
import com.shenhua.nandagy.widget.InnerGridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 学工
 * Created by Shenhua on 8/28/2016.
 */
public class XueGongFragment extends BaseFragment implements XueGongView, GridView.OnItemClickListener {

    @Bind(R.id.banner)
    BannerView bannerView;
    @Bind(R.id.gv_xue_tool)
    InnerGridView mInnerGridView;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.layout_empty)
    LinearLayout mEmptyLayout;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    private View view;
    private boolean isInit;
    private XueGongPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_xuegong, container, false);
            ButterKnife.bind(this, view);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            System.out.println("shenhua sout:" + "xuexuexue");
            presenter = new XueGongPresenter(this, "");
            presenter.execute();
            makeToolView(mInnerGridView, R.array.xuegong_tabs_titles, R.array.xuegong_tabs_images);
            mInnerGridView.setOnItemClickListener(this);
            isInit = true;
        }
    }

    private void textOkhttp() {
        OkHttpClient client = new OkHttpClient();
        Request request;
        request = new Request.Builder().url("http://www.ndgy.cn").build();
        try {
            Response response;
            response = client.newCall(request).execute();
            System.out.println("shenhua sout:ok http code:" + response.code());
            String result;
            result = new String(response.body().bytes());
            if (response.code() == 200) {
                if (result.contains("location")) {
                    Pattern p = Pattern.compile("location=\"(.*?)\"");
                    Matcher m = p.matcher(result);
                    String verify = "";
                    while (m.find()) {
                        MatchResult mr = m.toMatchResult();
                        verify = mr.group(1);
                    }
                    request = new Request.Builder().url("http://www.ndgy.cn" + verify).build();
                    response = client.newCall(request).execute();
                    System.out.println("shenhua sout:ok http code:" + response.code());
                    result = new String(response.body().bytes());
                    System.out.println("shenhua sout:2--->" + result);
                }
                System.out.println("shenhua sout:1-->" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateList(final ArrayList[] lists, @HttpManager.DataLoadType.DataLoadTypeChecker int type) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (lists == null) {
                    mNestedScrollView.setVisibility(View.GONE);
                    mEmptyLayout.setVisibility(View.VISIBLE);
                } else {
                    mNestedScrollView.setVisibility(View.VISIBLE);
                    updataBanner(lists[0]);
                    updateDatas(lists[1]);
                }
            }
        });
    }

    private void updataBanner(List<XueGongData.BannerData> list) {
        if (list != null) {
            String[] imgs = new String[list.size()];
            String[] titles = new String[list.size()];
            final String[] hrefs = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                imgs[i] = list.get(i).getbImage();
                titles[i] = list.get(i).getbTitle();
                hrefs[i] = list.get(i).getbHref();
            }
            bannerView.setBannerStyle(BannerView.BannerViewConfig.CIRCLE_INDICATOR_TITLE_HORIZONTAL);
            bannerView.setImageArray(imgs);
            bannerView.setBannerTitleArray(titles);
            bannerView.setOnBannerClickListener(new BannerView.OnBannerItemClickListener() {
                @Override
                public void OnBannerClick(View view, int position) {
                    Toast.makeText(getActivity(), hrefs[position - 1], Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateDatas(List<XueGongData> list) {
        if (list != null) {
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            final XueGongDataAdapter adapter = new XueGongDataAdapter(getContext(), list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    navToDetail(view, adapter.getDatas().get(position));
                }
            });
        }
    }

    private void navToDetail(View view, XueGongData xueGongData) {
        // http://blog.csdn.net/jxxfzgy/article/details/44515351
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        intent.putExtra("photo", xueGongData.getNewsType());
        intent.putExtra("title", xueGongData.getTitle());
        intent.putExtra("time", xueGongData.getTime());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_xuegong_img), "photos");
            getActivity().startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }

    @Override
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ProgressEventBus(true));
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ProgressEventBus(false));
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        onRead();
    }

    public void onRead() {
        System.out.println("shenhua sout:" + "重新加载");
        presenter.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("title", "部门概况");
                intent.putExtra("url", "school-xuegong-survey.html");
                sceneTransitionTo(intent, 0, view, R.id.tv_title, "title");
                break;
            case 1:
                toast("使用原生界面");
                break;
            case 2:
                toast("使用原生界面");
                break;
        }
    }
}
