package com.shenhua.nandagy.module.more.score.presenter;

import android.content.Context;

import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.module.jiaowu.model.ScoreModelImpl;
import com.shenhua.nandagy.module.more.score.view.ScoreView;

import java.util.List;

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

    public void login(Context context, String url, String num, String pwd) {
        scoreView.showProgress("正在登录教务系统");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<Integer>() {
            @Override
            public Integer doChildThread() {
                return scoreModel.login(context, url, num, pwd);
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
                        scoreView.showToast("服务器未响应");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.LOGIN_ERROR:
                        scoreView.showToast("服务器正在维护当中");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.LOGIN_CATCH:
                        // TODO: 4/6/2017 从缓存中拿数据
                        scoreView.showToast("登录失败");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.NETWORK_ABNORMAL:
                        scoreView.showToast("网络异常");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.NETWORK_TIMEOUT:
                        scoreView.showToast("连接超时");
                        scoreView.exit();
                        break;
                    case ScoreModelImpl.LOGIN_SUCCESS:
                        String[] numName = scoreModel.getmNumName();
                        scoreView.onLoginSuccess(numName);
//                        MyUser user = UserUtils.getInstance().isBinding(context);
//                        UserUtils.getInstance().updateUserInfo(context, "name", numName[1]);
//                        String objectId = user.getUserId();
//                        user.setName_num(DESUtils.getInstance().encrypt(numName[0]));
//                        user.setName(DESUtils.getInstance().encrypt(numName[1]));
//                        user.setName_id(user.getName_id());
//                        user.setInfo("update");
//                        user.update(objectId, new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//
//                            }
//                        });
                        break;
                }
            }
        });
    }

    public void getExamScore(Context context) {
        scoreView.showProgress("正在查询考试成绩");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<ExamScore>() {
            @Override
            public ExamScore doChildThread() {
                return scoreModel.getExamScore(context);
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

    public void getGradeScore(Context context) {
        scoreView.showProgress("正在查询等级考试成绩");
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<List<GradeScore>>() {
            @Override
            public List<GradeScore> doChildThread() {
                return scoreModel.getGradeScore(context);
            }

            @Override
            public void doUiThread(List<GradeScore> gradeScores) {
                scoreView.hideProgress();
                if (gradeScores != null) {
                    if (gradeScores.size() == 0) {
                        scoreView.getScoreFailed("你没有参加过考试");
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
