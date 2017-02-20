package com.shenhua.nandagy.ui.fragment.home;

import com.shenhua.nandagy.base.BaseHomeContentFragment;

/**
 * 工作动态
 * Created by shenhua on 8/29/2016.
 */
public class HomeGzFragment extends BaseHomeContentFragment {

    public static HomeGzFragment newInstance() {
        return new HomeGzFragment();
    }

    @Override
    public void init() {
        System.out.println("shenhua sout:" + "111111");
        presenter.execute();
    }
}
