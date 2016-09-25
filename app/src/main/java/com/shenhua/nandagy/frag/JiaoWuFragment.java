package com.shenhua.nandagy.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseFragment;
import com.shenhua.nandagy.widget.InnerGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 教务处
 * Created by Shenhua on 8/28/2016.
 */
public class JiaoWuFragment extends BaseFragment implements GridView.OnItemClickListener {

    @Bind(R.id.jw_tv_zc)
    TextView mWeeks;
    @Bind(R.id.gv_jiaowu_tool)
    InnerGridView mInnerGridView;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_jiaowu, container, false);
            ButterKnife.bind(this, view);
            initToolView();
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    private void initToolView() {
        makeToolView(mInnerGridView, R.array.jiaowu_tabs_titles, R.array.jiaowu_tabs_images);
        mInnerGridView.setOnItemClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EventBus.getDefault().post(new ProgressEventBus(false));
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
}
