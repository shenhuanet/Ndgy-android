package com.shenhua.nandagy.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.base.BaseActivity;
import com.shenhua.nandagy.base.BaseImageTextItem;
import com.shenhua.nandagy.bean.bmobbean.BombUtil;
import com.shenhua.nandagy.frag.score.ScoreBecFragment;
import com.shenhua.nandagy.frag.score.ScoreCAPFragment;
import com.shenhua.nandagy.frag.score.ScoreCetFragment;
import com.shenhua.nandagy.frag.score.ScoreComputerFragment;
import com.shenhua.nandagy.frag.score.ScoreFlushFragment;
import com.shenhua.nandagy.frag.score.ScoreForeignFragment;
import com.shenhua.nandagy.frag.score.ScoreMandarinFragment;
import com.shenhua.nandagy.frag.score.ScoreNTCEFragment;
import com.shenhua.nandagy.frag.score.ScorePETSFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * 成绩查询界面
 * Created by Shenhua on 9/7/2016.
 */
public class MoreScoreActivity extends BaseActivity {

    @Bind(R.id.list_scroe_category)
    ListView mListView;
    public static final int DEFAULT_POSITION = 0;
    private int currentItem = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, BombUtil.APP_KEY);
        setContentView(R.layout.activity_more_score);
        ButterKnife.bind(this);
        setupActionBar("成绩查询", true);
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
        Adapter adapter = new Adapter(this, items);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCategoryClicked(position);
            }
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

    private class Adapter extends BaseAdapter {

        private Context context;
        private List<BaseImageTextItem> lists;

        public Adapter(Context context, List<BaseImageTextItem> lists) {
            this.context = context;
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_score_category, parent, false);
                holder.mListViewItem = (LinearLayout) convertView.findViewById(R.id.common_layout_item);
                holder.nameTv = (TextView) convertView.findViewById(R.id.common_txt_name);
                holder.nameIv = (ImageView) convertView.findViewById(R.id.common_iv_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mListViewItem.setBackground(context.getResources().getDrawable(R.drawable.bg_score_category_item));
            BaseImageTextItem t = lists.get(position);
            holder.nameTv.setText(t.getTitle());
            Glide.with(context).load(t.getDrawable()).centerCrop().into(holder.nameIv);
            return convertView;
        }

        private class ViewHolder {
            LinearLayout mListViewItem;
            ImageView nameIv;
            TextView nameTv;
        }
    }

}
