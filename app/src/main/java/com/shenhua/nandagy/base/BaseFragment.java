package com.shenhua.nandagy.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.shenhua.commonlibs.base.BaseImageTextItem;
import com.shenhua.commonlibs.base.BaseListAdapter;
import com.shenhua.nandagy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment 基类
 * Created by Shenhua on 9/22/2016.
 */
public class BaseFragment extends Fragment {

    public void makeToolView(AbsListView abs, int titlesResId, int imagesResId) {
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
        @SuppressWarnings("unchecked")
        BaseListAdapter adapter = new BaseListAdapter(getActivity(), items) {

            @Override
            public void onBindItemView(BaseViewHolder baseViewHolder, Object o, int i) {
                baseViewHolder.setImageResource(R.id.iv_img, ((BaseImageTextItem) o).getDrawable());
                baseViewHolder.setText(R.id.tv_title, ((BaseImageTextItem) o).getTitle());
            }

            @Override
            public int getItemViewId() {
                return R.layout.item_common_imgtv;
            }
        };
        abs.setAdapter(adapter);
    }

    public void sceneTransitionTo(Intent intent, int resquestCode, View v, int viewId, String sharedElementName) {
        if (Build.VERSION.SDK_INT > 21) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    v.findViewById(viewId), sharedElementName);
            startActivityForResult(intent, resquestCode, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(v,
                    v.getWidth() / 2, v.getHeight() / 2, 0, 0);
            startActivityForResult(intent, resquestCode, options.toBundle());
        }
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
