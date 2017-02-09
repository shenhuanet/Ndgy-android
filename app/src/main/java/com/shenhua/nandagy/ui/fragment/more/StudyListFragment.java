package com.shenhua.nandagy.ui.fragment.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenhua.nandagy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyListFragment extends Fragment {

    @Bind(R.id.tv_t)
    TextView textView;

    public static StudyListFragment getInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        StudyListFragment fragment = new StudyListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_study, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("shenhua sout:" + "type:-" + getArguments().getInt("type"));
        textView.setText("" + getArguments().getInt("type"));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
