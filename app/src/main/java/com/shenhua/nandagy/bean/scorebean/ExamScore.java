package com.shenhua.nandagy.bean.scorebean;

import java.io.Serializable;
import java.util.List;

/**
 * 考试成绩
 * Created by shenhua on 4/1/2017.
 * Email shenhuanet@126.com
 */
public class ExamScore implements Serializable {

    private static final long serialVersionUID = 5355583737770796470L;
    private Overview overview;
    private List<ExamScoreList> examScoreLists;

    public ExamScore() {
    }

    public ExamScore(Overview overview, List<ExamScoreList> examScoreLists) {
        this.overview = overview;
        this.examScoreLists = examScoreLists;
    }

    public Overview getOverview() {
        return overview;
    }

    public void setOverview(Overview overview) {
        this.overview = overview;
    }

    public List<ExamScoreList> getExamScoreLists() {
        return examScoreLists;
    }

    public void setExamScoreLists(List<ExamScoreList> examScoreLists) {
        this.examScoreLists = examScoreLists;
    }

    /**
     * 成绩概览（总）
     */
    public class Overview {
        private String requestCredit;// 要求学分
        private String gainCredit;// 获得学分
        private String noPassCredit;// 未通过学分
        private String needCredit;// 还需学分
        private String totalPeople;// 总人数
        private String averageCredit;// 平均学分
        private String averagePoint;// 平均绩点

        public Overview() {
        }

        public Overview(String requestCredit, String gainCredit, String noPassCredit, String needCredit,
                        String totalPeople, String averageCredit, String averagePoint) {
            this.requestCredit = requestCredit;
            this.gainCredit = gainCredit;
            this.noPassCredit = noPassCredit;
            this.needCredit = needCredit;
            this.totalPeople = totalPeople;
            this.averageCredit = averageCredit;
            this.averagePoint = averagePoint;
        }

        public String getRequestCredit() {
            return requestCredit;
        }

        public void setRequestCredit(String requestCredit) {
            this.requestCredit = requestCredit;
        }

        public String getGainCredit() {
            return gainCredit;
        }

        public void setGainCredit(String gainCredit) {
            this.gainCredit = gainCredit;
        }

        public String getNoPassCredit() {
            return noPassCredit;
        }

        public void setNoPassCredit(String noPassCredit) {
            this.noPassCredit = noPassCredit;
        }

        public String getNeedCredit() {
            return needCredit;
        }

        public void setNeedCredit(String needCredit) {
            this.needCredit = needCredit;
        }

        public String getTotalPeople() {
            return totalPeople;
        }

        public void setTotalPeople(String totalPeople) {
            this.totalPeople = totalPeople;
        }

        public String getAverageCredit() {
            return averageCredit;
        }

        public void setAverageCredit(String averageCredit) {
            this.averageCredit = averageCredit;
        }

        public String getAveragePoint() {
            return averagePoint;
        }

        public void setAveragePoint(String averagePoint) {
            this.averagePoint = averagePoint;
        }
    }

    /**
     * 成绩详情
     */
    public class ExamScoreList {
        private String year;// 学年
        private String term;// 学期
        private String code;// 课程代码
        private String name;// 课程名称
        private String credit;// 学分
        private String point;// 绩点
        private String usualGrade;// 平时成绩
        private String midGrade;// 期中成绩
        private String finalGrade;// 期末成绩
        private String testGrade;// 实验成绩
        private String makeGrade;// 补考成绩
        private String rebuildGrade;// 重修成绩
        private String allGrade;// 总成绩
        private String school;// 学院

        // 暂未使用
        private String nature;// 课程性质
        private String ascription;// 课程归属
        private String minor;// 辅修标记
        private String rebuild;// 重修标记
        private String remark;// 备注

        public ExamScoreList() {
        }

        public ExamScoreList(String year, String term, String code, String name, String credit,
                             String point, String usualGrade, String midGrade, String finalGrade,
                             String testGrade, String makeGrade, String rebuildGrade, String allGrade,
                             String school) {
            this.year = year;
            this.term = term;
            this.code = code;
            this.name = name;
            this.credit = credit;
            this.point = point;
            this.usualGrade = usualGrade;
            this.midGrade = midGrade;
            this.finalGrade = finalGrade;
            this.testGrade = testGrade;
            this.makeGrade = makeGrade;
            this.rebuildGrade = rebuildGrade;
            this.allGrade = allGrade;
            this.school = school;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getUsualGrade() {
            return usualGrade;
        }

        public void setUsualGrade(String usualGrade) {
            this.usualGrade = usualGrade;
        }

        public String getMidGrade() {
            return midGrade;
        }

        public void setMidGrade(String midGrade) {
            this.midGrade = midGrade;
        }

        public String getFinalGrade() {
            return finalGrade;
        }

        public void setFinalGrade(String finalGrade) {
            this.finalGrade = finalGrade;
        }

        public String getTestGrade() {
            return testGrade;
        }

        public void setTestGrade(String testGrade) {
            this.testGrade = testGrade;
        }

        public String getMakeGrade() {
            return makeGrade;
        }

        public void setMakeGrade(String makeGrade) {
            this.makeGrade = makeGrade;
        }

        public String getRebuildGrade() {
            return rebuildGrade;
        }

        public void setRebuildGrade(String rebuildGrade) {
            this.rebuildGrade = rebuildGrade;
        }

        public String getAllGrade() {
            return allGrade;
        }

        public void setAllGrade(String allGrade) {
            this.allGrade = allGrade;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }
    }
}
