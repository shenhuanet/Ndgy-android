package com.shenhua.nandagy.activity;

import android.content.Context;
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
import com.shenhua.nandagy.bean.CommonItemData;
import com.shenhua.nandagy.bean.bmobbean.BombUtil;
import com.shenhua.nandagy.frag.ScoreBecFragment;
import com.shenhua.nandagy.frag.ScoreCAPFragment;
import com.shenhua.nandagy.frag.ScoreCETFragment;
import com.shenhua.nandagy.frag.ScoreCETKFragment;
import com.shenhua.nandagy.frag.ScoreComputerFragment;
import com.shenhua.nandagy.frag.ScoreFlushFragment;
import com.shenhua.nandagy.frag.ScoreForeignFragment;
import com.shenhua.nandagy.frag.ScoreMandarinFragment;
import com.shenhua.nandagy.frag.ScoreNTCEFragment;
import com.shenhua.nandagy.frag.ScorePETSFragment;

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
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        setupActionBar("成绩查询", true);
        setupView();
    }

    private void setupView() {
        Adapter adapter = new Adapter(this, getTypeList());
        mListView.setAdapter(adapter);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCategoryClicked(position);
            }
        });
        onCategoryClicked(DEFAULT_POSITION);
    }

    private List<CommonItemData> getTypeList() {
        String[] title = getResources().getStringArray(R.array.category_score_item);
        int[] resId = {R.drawable.ic_score_cet_normal, R.drawable.ic_score_cetk_normal, R.drawable.ic_score_mandarin_normal,
                R.drawable.ic_score_pets_normal, R.drawable.ic_score_computer_normal, R.drawable.ic_score_bec_normal,
                R.drawable.ic_score_flush_normal, R.drawable.ic_score_ntce_normal, R.drawable.ic_score_cap_normal,
                R.drawable.ic_score_forign_normal,};
        List<CommonItemData> items = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            CommonItemData item = new CommonItemData();
            item.setTitle(title[i]);
            item.setResId(resId[i]);
            items.add(item);
        }
        return items;
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
                fragment = ScoreCETFragment.newInstance();
                break;
            case 1:
                fragment = ScoreCETKFragment.newInstance();
                break;
            case 2:
                fragment = ScoreMandarinFragment.newInstance();
                break;
            case 3:
                fragment = ScorePETSFragment.newInstance();
                break;
            case 4:
                fragment = ScoreComputerFragment.newInstance();
                break;
            case 5:
                fragment = ScoreBecFragment.newInstance();
                break;
            case 6:
                fragment = ScoreFlushFragment.newInstance();
                break;
            case 7:
                fragment = ScoreNTCEFragment.newInstance();
                break;
            case 8:
                fragment = ScoreCAPFragment.newInstance();
                break;
            case 9:
                fragment = ScoreForeignFragment.newInstance();
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
        private List<CommonItemData> lists;

        public Adapter(Context context, List<CommonItemData> lists) {
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
            CommonItemData t = lists.get(position);
            holder.nameTv.setText(t.getTitle());
            Glide.with(context).load(t.getResId()).centerCrop().into(holder.nameIv);
            return convertView;
        }

        private class ViewHolder {
            LinearLayout mListViewItem;
            ImageView nameIv;
            TextView nameTv;
        }
    }

}
