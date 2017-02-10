package com.shenhua.nandagy.ui.fragment.home;

import com.shenhua.nandagy.base.BaseHomeContentFragment;
import com.shenhua.nandagy.presenter.HomePresenter;

/**
 * 公告公示
 * Created by shenhua on 8/29/2016.
 */
public class HomeGgFragment extends BaseHomeContentFragment {

    private static HomeGgFragment instance = null;

    public static HomeGgFragment newInstance() {
        if (instance == null)
            instance = new HomeGgFragment();
        return instance;
    }

    @Override
    public void init() {
        System.out.println("shenhua sout:" + "222222");
        presenter = new HomePresenter(this, "77");
        presenter.execute();
    }
}
