package com.shenhua.nandagy.ui.fragment.jiaowu;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.commonlibs.mvp.BaseMvpFragment;
import com.shenhua.commonlibs.utils.BusBooleanEvent;
import com.shenhua.commonlibs.utils.BusProvider;
import com.shenhua.commonlibs.widget.InnerGridView;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.JiaowuDataAdapter;
import com.shenhua.nandagy.bean.ContentPassesData;
import com.shenhua.nandagy.bean.JiaowuData;
import com.shenhua.nandagy.presenter.JiaowuPresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ContentDetailType;
import com.shenhua.nandagy.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.view.JiaowuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 教务处
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_jiaowu)
public class JiaoWuFragment extends BaseMvpFragment<JiaowuPresenter, JiaowuView> implements JiaowuView, GridView.OnItemClickListener {

    private static final String TAG = "JiaoWuFragment";
    @BindView(R.id.jw_tv_zc)
    TextView mWeeks;
    @BindView(R.id.gv_jiaowu_tool)
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
    private JiaowuPresenter presenter;

    @Override
    public void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit) {
            presenter.execute();
            setupToolView(mInnerGridView, R.array.jiaowu_tabs_titles, R.array.jiaowu_tabs_images);
            mInnerGridView.setOnItemClickListener(this);
            isInit = true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                toast("000");
                break;
            case 1:
                toast("111");
                break;
            case 2:
                toast("222");
                break;
        }
    }

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
        BaseListAdapter adapter = new BaseListAdapter<BaseImageTextItem>(getActivity(), items) {

            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int i) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

    @Override
    public JiaowuPresenter createPresenter() {
        presenter = new JiaowuPresenter(this, Constants.JIAOWU_URL);
        return presenter;
    }

    @Override
    public void updateList(JiaowuData data) {
        String week = data.getWeek();
        mWeeks.setText(week);
        List<JiaowuData.JiaowuList> lists = data.getList();
        if (lists != null) {
            mNestedScrollView.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.INVISIBLE);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            JiaowuDataAdapter adapter = new JiaowuDataAdapter(getContext(), lists);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((view, position, data1) -> {
                navToDetail(view, data.getList().get(position));
            });
        } else {
            mNestedScrollView.setVisibility(View.INVISIBLE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

    private void navToDetail(View view, JiaowuData.JiaowuList data) {
        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
        ContentPassesData contentPassesData = new ContentPassesData(
                ContentDetailType.TYPE_JIAOWU,
                data.getTitle(),
                data.getDrawable(),
                data.getTime(),
                data.getHref());
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", contentPassesData);
        intent.putExtras(bundle);
        sceneTransitionTo(intent, 0, view, R.id.circleImageView, "photos");
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

    @Override
    public void showToast(String msg) {
        toast(msg);
    }

}
