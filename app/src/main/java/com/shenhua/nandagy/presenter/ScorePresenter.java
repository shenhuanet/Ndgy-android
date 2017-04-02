package com.shenhua.nandagy.presenter;

import android.content.Context;

import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.nandagy.bean.bmobbean.MyUser;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.model.ScoreModelImpl;
import com.shenhua.nandagy.utils.bmobutils.UserUtils;
import com.shenhua.nandagy.view.ScoreView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Shenhua on 4/1/2017.
 * e-mail shenhuanet@126.com
 */
public class ScorePresenter {

    private ScoreView scoreView;
    private ScoreModelImpl scoreModel;

    public ScorePresenter(ScoreView scoreView) {
        this.scoreView = scoreView;
        scoreModel = new ScoreModelImpl();
    }

    public void login(final Context context, String url) {
        scoreView.showProgress("正在登录教务系统");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<Integer>() {
            @Override
            public Integer doChildThread() {
                return scoreModel.login(url);
            }

            @Override
            public void doUiThread(Integer result) {
                scoreView.hideProgress();
                switch (result) {
                    case ScoreModelImpl.LOGIN_USERNAME_ERROR:
                        scoreView.reBinding("用户名不存在或未按照要求参加教学活动");
                        break;
                    case ScoreModelImpl.LOGIN_PASSWORE_ERROR:
                        scoreView.reBinding("密码错误");
                        break;
                    case ScoreModelImpl.LOGIN_REDIRCT_HOME:
                        scoreView.showToast("好像跳转到了首页");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.LOGIN_ERROR:
                        scoreView.showToast("服务器正在维护当中");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.LOGIN_SUCCESS:
                        String[] numName = scoreModel.getmNumName();
                        scoreView.onLoginSuccess(numName);
                        MyUser user = UserUtils.getInstance().getUser(context);
                        String objectId = user.getUserId();
                        user.setName_num("");
                        user.setName_id("");
                        user.setInfo("update");
                        user.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
//                                    UserUtils.getInstance().updateUserInfo(ScoreActivity.this, "name_num", num);
//                                    UserUtils.getInstance().updateUserInfo(ScoreActivity.this, "name_id", id);
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });

                        break;
                    default:

                        break;
                }
            }
        });
    }

    public void getExamScore(String url) {
        scoreView.showProgress("正在查询考试成绩");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<ExamScore>() {
            @Override
            public ExamScore doChildThread() {
                return scoreModel.getExamScore(url);
            }

            @Override
            public void doUiThread(ExamScore examScore) {
                scoreView.hideProgress();
                if (examScore != null) {
                    scoreView.showExamScore(examScore);
                } else {
                    scoreView.getScoreFailed("成绩获取失败");
                }
            }
        });
    }

    public void getGradeScore(String url) {
        scoreView.showProgress("正在查询等级考试成绩");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<List<GradeScore>>() {
            @Override
            public List<GradeScore> doChildThread() {
                return scoreModel.getGradeScore(url);
            }

            @Override
            public void doUiThread(List<GradeScore> gradeScores) {
                scoreView.hideProgress();
                if (gradeScores != null) {
                    if (gradeScores.size() == 0) {
                        // 没有参加考试
                    } else {
                        scoreView.showGradeScore(gradeScores);
                    }
                } else {
                    scoreView.getScoreFailed("成绩获取失败");
                }
            }
        });

    }
}
