package com.shenhua.nandagy.adapter;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.commonlibs.base.BaseRecyclerAdapter;
import com.shenhua.lib.emoji.utils.EmojiLoader;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.bmobbean.SchoolCircle;
import com.shenhua.nandagy.bean.bmobbean.UserZone;
import com.shenhua.nandagy.database.DaoMaster;
import com.shenhua.nandagy.database.GreatHateFav;
import com.shenhua.nandagy.database.GreatHateFavDao;
import com.shenhua.nandagy.utils.RelativeDateFormat;
import com.shenhua.nandagy.utils.bmobutils.AvatarUtils;
import com.shenhua.nandagy.utils.bmobutils.CircleDataLoader;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.utils.bmobutils.UserZoneUtils;
import com.shenhua.nandagy.widget.refresh.PacManRefreshHead;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Shenhua on 10/6/2016.
 * E-mail shenhuanet@126.com
 */
public class CircleAdapter extends BaseRecyclerAdapter<SchoolCircle> {

    private static final int TYPE_GREAT = 0;
    private static final int TYPE_HATE = 1;
    private static final int TYPE_FAV = 2;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_FOOTER = 3;

    private GreatHateFavDao dao;
    private boolean mShowFooter;
    private int lastPosition = -1;

    public CircleAdapter(Context context, List<SchoolCircle> datas) {
        super(context, datas);
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(mContext, "circle.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        dao = daoMaster.newSession().getGreatHateFavDao();
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowFooter && getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemViewId(int viewType) {
        if (viewType == TYPE_FOOTER) {
            return R.layout.item_footer;
        } else {
            return R.layout.item_circle;
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void showFooter() {
        notifyItemInserted(getItemCount());
        mShowFooter = true;
    }

    public void hideFooter() {
        notifyItemRemoved(getItemCount() - 1);
        mShowFooter = false;
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.itemView.getAnimation() != null && holder.itemView
                .getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    public void updateItem(int position, SchoolCircle data) {
        mDatas.set(position, data);
        notifyItemChanged(position);
    }

    @Override
    public void bindData(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, int position, SchoolCircle item) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            PacManRefreshHead head = (PacManRefreshHead) holder.getView(R.id.pac_man);
            head.performLoading();
        } else {
            // 用户名
            AvatarUtils.loadUserNick(item.getUserzone(), (TextView) holder.getView(R.id.tv_user_nick));
            // 时间
            holder.setText(R.id.tv_time_ago, RelativeDateFormat.friendly_time(item.getCreatedAt()));
            // 头像
            AvatarUtils.loadUserAvatar(mContext, item.getUserzone().getUser(), (ImageView) holder.getView(R.id.iv_user_photo));
            // 图片组
            List<BmobFile> file = item.getPics();
            GridView gridView = (GridView) holder.getView(R.id.gridView);
            gridView.setVisibility(View.GONE);
            if (file != null && file.size() > 0) {
                gridView.setVisibility(View.VISIBLE);
                gridView.setNumColumns(file.size() <= 3 ? file.size() : 3);
                CircleGridAdapter adapter = new CircleGridAdapter(mContext, file);
                gridView.setAdapter(adapter);
            }
            // 内容
            TextView content = (TextView) holder.getView(R.id.tv_content);
            content.setText(item.getContent());
            EmojiLoader.replaceEmoji(mContext, content);
            // 底部数据
            holder.setText(R.id.tv_comment, CircleDataLoader.formatNumber(item.getComment()));
            holder.setText(R.id.tv_hate, CircleDataLoader.formatNumber(item.getHate()));
            holder.setText(R.id.tv_great, CircleDataLoader.formatNumber(item.getGreat()));
            // 底部点击事件
            onGreatClick(holder, item);
            onHateClick(holder, item);
            onCommentClick(holder, item);
            onFavClick(holder, item);
            setAnimation(holder.itemView, position);
        }
    }

    private void onFavClick(BaseRecyclerViewHolder holder, SchoolCircle item) {
        TextView textView = (TextView) holder.getView(R.id.tv_fav);
        boolean isFav = CircleDataLoader.getInstance().isFav(mContext, item.getObjectId(), dao);
        textView.setSelected(isFav);
        holder.getView(R.id.f_fav).setOnClickListener(v -> {
            if (!UserUtils.getInstance().isLogin()) {// 未登录
                Toast.makeText(mContext, "请登录后操作", Toast.LENGTH_SHORT).show();
                // nav to login
            } else if (!UserUtils.getInstance().isCreateZone()) {// 未创建空间
                Toast.makeText(mContext, "你当前还没有创建空间哦", Toast.LENGTH_SHORT).show();
                // TODO: 5/18/2017  nav to create zone
            } else {
                if (isFav) {// 已点赞
                    Toast.makeText(mContext, "你已收藏", Toast.LENGTH_SHORT).show();
                    textView.setSelected(true);
                } else {
                    UserZone userzone = UserZoneUtils.getInstance().getUserZone(mContext);
                    BmobRelation relation = new BmobRelation();
                    relation.add(item);
                    userzone.setFavorite(relation);
                    userzone.update(userzone.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
                                textView.setSelected(true);
                                setGreatOrHate(TYPE_FAV, item.getObjectId());
                            } else {
                                Toast.makeText(mContext, "收藏失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void onCommentClick(BaseRecyclerViewHolder holder, SchoolCircle item) {
        holder.getView(R.id.f_comment).setOnClickListener(v -> {
            mOnItemClickListener.OnItemClick(v, holder.getAdapterPosition(), item);
        });
    }

    private void onHateClick(BaseRecyclerViewHolder holder, SchoolCircle item) {
        TextView textView = (TextView) holder.getView(R.id.tv_hate);
        boolean isHate = CircleDataLoader.getInstance().isHate(mContext, item.getObjectId(), dao);
        textView.setSelected(isHate);
        holder.getView(R.id.f_hate).setOnClickListener(v -> {
            if (!UserUtils.getInstance().isLogin()) {// 未登录
                Toast.makeText(mContext, "请登录后操作", Toast.LENGTH_SHORT).show();
                // nav to login
            } else if (!UserUtils.getInstance().isCreateZone()) {// 未创建空间
                Toast.makeText(mContext, "你当前还没有创建空间哦", Toast.LENGTH_SHORT).show();
                // nav to create zone
            } else {
                if (isHate) {// 已踩
                    Toast.makeText(mContext, "你已点踩", Toast.LENGTH_SHORT).show();
                    textView.setSelected(true);
                } else {
                    UserZone userzone = UserZoneUtils.getInstance().getUserZone(mContext);
                    BmobRelation relation = new BmobRelation();
                    relation.add(userzone);
                    item.setHates(relation);
                    item.update(item.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "点踩成功", Toast.LENGTH_SHORT).show();
                                Integer integer = Integer.parseInt(textView.getText().toString());
                                textView.setText("" + (integer + 1));
                                textView.setSelected(true);
                                // 更新Bmob数据库
                                item.increment("hate");
                                item.update(item.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            setGreatOrHate(TYPE_HATE, item.getObjectId());
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(mContext, "点踩失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void onGreatClick(BaseRecyclerAdapter.BaseRecyclerViewHolder holder, SchoolCircle item) {
        TextView textView = (TextView) holder.getView(R.id.tv_great);
        boolean isGreat = CircleDataLoader.getInstance().isGreat(mContext, item.getObjectId(), dao);
        textView.setSelected(isGreat);
        holder.getView(R.id.f_great).setOnClickListener(v -> {
            if (!UserUtils.getInstance().isLogin()) {// 未登录
                Toast.makeText(mContext, "请登录后操作", Toast.LENGTH_SHORT).show();
                // nav to login
            } else if (!UserUtils.getInstance().isCreateZone()) {// 未创建空间
                Toast.makeText(mContext, "你当前还没有创建空间哦", Toast.LENGTH_SHORT).show();
                // nav to create zone
            } else {
                if (isGreat) {// 已点赞
                    Toast.makeText(mContext, "你已点赞", Toast.LENGTH_SHORT).show();
                    textView.setSelected(true);
                } else {
                    UserZone userzone = UserZoneUtils.getInstance().getUserZone(mContext);
                    BmobRelation relation = new BmobRelation();
                    relation.add(userzone);
                    item.setLikes(relation);
                    item.update(item.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                                Integer integer = Integer.parseInt(textView.getText().toString());
                                textView.setText("" + (integer + 1));
                                textView.setSelected(true);
                                // 更新Bmob数据库
                                item.increment("great");
                                item.update(item.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            setGreatOrHate(TYPE_GREAT, item.getObjectId());
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(mContext, "点赞失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setGreatOrHate(int type, String pObj) {
        UserZone uz = UserZoneUtils.getInstance().getUserZone(mContext);
        if (uz != null) {
            List<GreatHateFav> fav = dao.queryBuilder().where(
                    GreatHateFavDao.Properties.UserzoneId.eq(uz.getObjectId()), GreatHateFavDao.Properties.PostId.eq(pObj)
            ).build().list();
            if (fav != null && fav.size() > 0) {// 已经存在
                GreatHateFav ghf = fav.get(0);
                if (type == TYPE_GREAT) {
                    ghf.setIsGreat(true);
                } else if (type == TYPE_FAV) {
                    ghf.setIsFav(true);
                } else {
                    ghf.setIsHate(true);
                }
                dao.update(ghf);
            } else {
                GreatHateFav ghf = new GreatHateFav();
                ghf.setUserzoneId(uz.getObjectId());
                ghf.setPostId(pObj);
                if (type == TYPE_GREAT) {
                    ghf.setIsGreat(true);
                } else if (type == TYPE_FAV) {
                    ghf.setIsFav(true);
                } else {
                    ghf.setIsHate(true);
                }
                dao.insert(ghf);
            }
        }
    }

}
