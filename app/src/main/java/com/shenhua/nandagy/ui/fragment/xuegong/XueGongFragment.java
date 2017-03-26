package com.shenhua.nandagy.ui.fragment.xuegong;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.commonlibs.mvp.BaseMvpFragment;
import com.shenhua.commonlibs.utils.BusBooleanEvent;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.commonlibs.widget.InnerGridView;
import com.shenhua.libs.bannerview.BannerView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.XueGongDataAdapter;
import com.shenhua.nandagy.bean.ContentPassesData;
import com.shenhua.nandagy.bean.XueGongData;
import com.shenhua.nandagy.presenter.XueGongPresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ContentDetailType;
import com.shenhua.nandagy.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.ui.activity.WebActivity;
import com.shenhua.nandagy.ui.activity.xuegong.EduAdminActivity;
import com.shenhua.nandagy.ui.activity.xuegong.FinanceActivity;
import com.shenhua.nandagy.view.XueGongView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学工
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_xuegong)
public class XueGongFragment extends BaseMvpFragment<XueGongPresenter, XueGongView> implements XueGongView {

    @BindView(R.id.banner)
    BannerView bannerView;
    @BindView(R.id.gv_xue_tool)
    InnerGridView mInnerGridView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.layout_empty)
    LinearLayout mEmptyLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private boolean isInit;
    private XueGongPresenter presenter;

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            presenter.execute();
            setupToolView(mInnerGridView, R.array.xuegong_tabs_titles, R.array.xuegong_tabs_images);
            isInit = true;
        }
    }

    @Override
    public XueGongPresenter createPresenter() {
        presenter = new XueGongPresenter(this, Constants.XUEGONG_URL);
        return presenter;
    }

    @Override
    public void updateList(final ArrayList[] lists) {
        if (lists == null) {
            mNestedScrollView.setVisibility(View.INVISIBLE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mNestedScrollView.setVisibility(View.VISIBLE);
            updateBanner(lists[0]);
            updateDatas(lists[1]);
            mEmptyLayout.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示banner
     *
     * @param list List<XueGongData.BannerData>
     */
    private void updateBanner(List<XueGongData.BannerData> list) {
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
            bannerView.setOnBannerClickListener((view, position) ->
                    toast(hrefs[position - 1]));
        }
    }

    /**
     * 显示列表
     *
     * @param list List<XueGongData>
     */
    private void updateDatas(List<XueGongData> list) {
        if (list != null) {
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            final XueGongDataAdapter adapter = new XueGongDataAdapter(getContext(), list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((view1, i, data) -> navToDetail(view1, data));
        }
    }

    private void navToDetail(View view, XueGongData data) {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        ContentPassesData contentPassesData = new ContentPassesData(
                ContentDetailType.TYPE_JIAOWU,
                data.getTitle(),
                data.getNewsType(),
                data.getTime(),
                data.getHref());
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", contentPassesData);
        intent.putExtras(bundle);
        sceneTransitionTo(intent, 0, view, R.id.iv_xuegong_img, "photos");
    }

    @Override
    public void showToast(final String msg) {
        toast(msg);
    }

    @Override
    public void showProgress() {
        BusProvider.getInstance().post(new BusBooleanEvent(true));
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        BusProvider.getInstance().post(new BusBooleanEvent(false));
        mProgressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        presenter.execute();
    }

    /**
     * 设置中间的3个toolView
     *
     * @param abs         listView
     * @param titlesResId titlesId
     * @param imagesResId imagesId
     */
    public void setupToolView(AbsListView abs, int titlesResId, int imagesResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abs.setNestedScrollingEnabled(false);
        }
        List<BaseImageTextItem> items = new ArrayList<>();
        String[] titles = getResources().getStringArray(titlesResId);
        TypedArray ar = getResources().obtainTypedArray(imagesResId);
        for (int i = 0; i < titles.length; i++) {
            BaseImageTextItem item = new BaseImageTextItem(ar.getResourceId(i, 0), titles[i]);
            items.add(item);
        }
        ar.recycle();
        Class[] classes = {WebActivity.class, EduAdminActivity.class, FinanceActivity.class};
        BaseListAdapter adapter = new BaseListAdapter<BaseImageTextItem>(getContext(), items) {

            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int position) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
                baseViewHolder.getView(R.id.rootView).setOnClickListener(view -> {
                    Intent intent = new Intent(getContext(), classes[position]);
                    if (position == 0) {
                        intent.putExtra("title", "部门概况");
                        intent.putExtra("url", "school-xuegong-survey.html");
                    }
                    sceneTransitionTo(intent, 0, view, R.id.tv_title, "title");
                });
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

}
