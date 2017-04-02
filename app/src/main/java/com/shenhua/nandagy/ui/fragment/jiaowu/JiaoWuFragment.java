package com.shenhua.nandagy.ui.fragment.jiaowu;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.presenter.JiaowuPresenter;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ContentDetailType;
import com.shenhua.nandagy.ui.activity.ContentDetailActivity;
import com.shenhua.nandagy.ui.activity.jiaowu.BindingActivity;
import com.shenhua.nandagy.ui.activity.jiaowu.ScoreActivity;
import com.shenhua.nandagy.ui.activity.me.LoginActivity;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.view.JiaowuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 教务处
 * Created by Shenhua on 8/28/2016.
 */
@ActivityFragmentInject(contentViewId = R.layout.frag_jiaowu)
public class JiaoWuFragment extends BaseMvpFragment<JiaowuPresenter, JiaowuView> implements JiaowuView {

    private static final String TAG = "JiaoWuFragment";
    @BindView(R.id.jw_tv_zc)
    TextView mWeeks;
    @BindView(R.id.gv_jiaowu_tool)
    InnerGridView mInnerGridView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
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
            isInit = true;
        }
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

        mEmptyLayout.setVisibility(View.INVISIBLE);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        JiaowuDataAdapter adapter = new JiaowuDataAdapter(getContext(), lists);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position, data1) -> {
            navToDetail(view, data.getList().get(position));
        });
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
        mEmptyLayout.setVisibility(View.VISIBLE);
        toast(msg);
    }

    @OnClick(R.id.layout_empty_reload)
    void onClick(View v) {
        presenter.execute();
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
            public void onBindItemView(BaseViewHolder baseViewHolder, BaseImageTextItem item, int position) {
                baseViewHolder.setImageResource(R.id.iv_img, item.getDrawable());
                baseViewHolder.setText(R.id.tv_title, item.getTitle());
                baseViewHolder.setOnListItemClickListener((view -> {
                    if (position == 0) {
                        if (UserUtils.getInstance().isLogin(getContext())) {
                            doIsBinding(true);
                        } else {
                            toast("请先登录后再使用本功能");
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivityForResult(intent, Constants.Code.REQUEST_CODE_NAV_TO_LOGIN);
                        }
                    }
                    if (position == 1) toast("现在不是选课时间");
                    if (position == 2) toast("现在不是报名时间");
                }));
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

    private void doIsBinding(boolean showMsg) {
        if (UserUtils.getInstance().isBinding(getContext())) {
            MyUser user = UserUtils.getInstance().getUser(getContext());
            Intent intent = new Intent(getContext(), ScoreActivity.class);
            intent.putExtra("name_num", user.getName_num());
            intent.putExtra("name_id", user.getName_id());
            startActivity(intent);
        } else {
            if (showMsg) {
                toast("请先登录教务系统");
            }
            Intent intent = new Intent(getContext(), BindingActivity.class);
            startActivityForResult(intent, Constants.Code.REQUEST_CODE_NAV_TO_BINDING);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_LOGIN) {
            if (resultCode == Constants.Code.RECULT_CODE_LOGIN_SUCCESS) {
                doIsBinding(false);
            } else {
                toast("登录取消");
            }
        }
        if (requestCode == Constants.Code.REQUEST_CODE_NAV_TO_BINDING) {
            if (resultCode != Constants.Code.RECULT_CODE_BINDING_SUCCESS) {
                toast("取消操作");
            }
        }
    }
}
