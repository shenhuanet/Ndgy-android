package com.shenhua.nandagy.ui.fragment.home;

import com.shenhua.nandagy.base.BaseHomeContentFragment;
import com.shenhua.nandagy.presenter.HomePresenter;

/**
 * 工作动态
 * Created by shenhua on 8/29/2016.
 */
public class HomeGzFragment extends BaseHomeContentFragment {

    private static HomeGzFragment instance = null;

    public static HomeGzFragment newInstance() {
        if (instance == null)
            instance = new HomeGzFragment();
        return instance;
    }

    @Override
    public void init() {
        System.out.println("shenhua sout:" + "111111");
        presenter = new HomePresenter(this, "");
        presenter.execute();
    }

}
