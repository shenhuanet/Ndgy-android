package com.shenhua.nandagy.ui.activity.more;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.AbsListView;
import android.widget.ListView;

import com.shenhua.commonlibs.annotation.ActivityFragmentInject;
import com.shenhua.commonlibs.base.BaseActivity;
import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.adapter.ScoreQueryCategoryAdapter;
import com.shenhua.nandagy.bean.bmobbean.BombUtil;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreBecFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreCAPFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreCetFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreComputerFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreFlushFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreForeignFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreMandarinFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScoreNTCEFragment;
import com.shenhua.nandagy.ui.fragment.scorequery.ScorePETSFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * 成绩查询界面
 * Created by Shenhua on 9/7/2016.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_more_score,
        toolbarId = R.id.common_toolbar,
        toolbarHomeAsUp = true,
        toolbarTitle = R.string.toolbar_title_score_query,
        toolbarTitleId = R.id.toolbar_title
)
public class ScoreQueryActivity extends BaseActivity {

    @BindView(R.id.list_scroe_category)
    ListView mListView;
    public static final int DEFAULT_POSITION = 0;
    private int currentItem = -1;

    @Override
    protected void initView(BaseActivity baseActivity) {
        Bmob.initialize(this, BombUtil.APP_KEY);
        ButterKnife.bind(this);
        makeCategoryView(R.array.category_score_item, R.array.category_score_images);
    }

    public void makeCategoryView(int titlesResId, int imagesResId) {
        List<BaseImageTextItem> items = new ArrayList<>();
        String[] titles = getResources().getStringArray(titlesResId);
        TypedArray ar = getResources().obtainTypedArray(imagesResId);
        for (int i = 0; i < titles.length; i++) {
            BaseImageTextItem item = new BaseImageTextItem(ar.getResourceId(i, 0), titles[i]);
            items.add(item);
        }
        ar.recycle();
        ScoreQueryCategoryAdapter adapter = new ScoreQueryCategoryAdapter(this, items);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            onCategoryClicked(position);
            hideKeyboard();
        });
        onCategoryClicked(DEFAULT_POSITION);
    }

    private void onCategoryClicked(int position) {
        if (position == currentItem)
            return;
        mListView.setItemChecked(position, true);
        navTo(position);
        currentItem = position;
    }

    private void navTo(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ScoreCetFragment.newInstance();
                break;
            case 1:
                fragment = ScoreMandarinFragment.newInstance();
                break;
            case 2:
                fragment = ScorePETSFragment.newInstance();
                break;
            case 3:
                fragment = ScoreComputerFragment.newInstance();
                break;
            case 4:
                fragment = ScoreBecFragment.newInstance();
                break;
            case 5:
                fragment = ScoreFlushFragment.newInstance();
                break;
            case 6:
                fragment = ScoreForeignFragment.newInstance();
                break;
            case 7:
                fragment = ScoreNTCEFragment.newInstance();
                break;
            case 8:
                fragment = ScoreCAPFragment.newInstance();
                break;
        }
        navigateTo(fragment);
    }

    private void navigateTo(Fragment newFragment) {
        if (newFragment == null) return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_score, newFragment);
        ft.commit();
    }

}
