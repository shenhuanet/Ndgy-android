package com.shenhua.lib.emoji.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenhua on 5/2/2017.
 * Email shenhuanet@126.com
 */
public class EmojiGroup implements Serializable {
    private static final long serialVersionUID = 5424372406974499352L;

    /**
     * groupId : 0
     * groupIcon : emoji_weico.png
     * groupSize : 62
     * emoji : [{"id":"001","tag":"[xx]","file":"emoji_001.png"},{"id":"002","tag":"[hh]","file":"emoji_002.png"},{"id":"003","tag":"[aini]","file":"emoji_003.png"},{"id":"004","tag":"[xk]","file":"emoji_004.png"},{"id":"005","tag":"[beis]","file":"emoji_005.png"},{"id":"006","tag":"[bis]","file":"emoji_006.png"},{"id":"007","tag":"[biz]","file":"emoji_007.png"},{"id":"008","tag":"[canz]","file":"emoji_008.png"},{"id":"009","tag":"[cj]","file":"emoji_009.png"},{"id":"010","tag":"[dhq]","file":"emoji_010.png"},{"id":"011","tag":"[dl]","file":"emoji_011.png"},{"id":"012","tag":"[ding]","file":"emoji_012.png"},{"id":"013","tag":"[ka]","file":"emoji_013.png"},{"id":"014","tag":"[hx]","file":"emoji_014.png"},{"id":"015","tag":"[gz]","file":"emoji_015.png"},{"id":"016","tag":"[haha]","file":"emoji_016.png"},{"id":"017","tag":"[han]","file":"emoji_017.png"},{"id":"018","tag":"[ldln]","file":"emoji_018.png"},{"id":"019","tag":"[jiyan]","file":"emoji_019.png"},{"id":"020","tag":"[kelian]","file":"emoji_020.png"},{"id":"021","tag":"[heixian]","file":"emoji_021.png"},{"id":"022","tag":"[huanxin]","file":"emoji_022.png"},{"id":"023","tag":"[kun]","file":"emoji_023.png"},{"id":"024","tag":"[qinqin]","file":"emoji_024.png"},{"id":"025","tag":"[yiwen]","file":"emoji_025.png"},{"id":"026","tag":"[yhh]","file":"emoji_026.png"},{"id":"027","tag":"[zhh]","file":"emoji_027.png"},{"id":"028","tag":"[sw]","file":"emoji_028.png"},{"id":"029","tag":"[xu]","file":"emoji_029.png"},{"id":"030","tag":"[tu]","file":"emoji_030.png"},{"id":"031","tag":"[tx]","file":"emoji_031.png"},{"id":"032","tag":"[tkx]","file":"emoji_032.png"},{"id":"033","tag":"[sk]","file":"emoji_033.png"},{"id":"034","tag":"[jx]","file":"emoji_034.png"},{"id":"035","tag":"[bb]","file":"emoji_035.png"},{"id":"036","tag":"[kub]","file":"emoji_036.png"},{"id":"037","tag":"[koub]","file":"emoji_037.png"},{"id":"038","tag":"[yun]","file":"emoji_038.png"},{"id":"039","tag":"[sj]","file":"emoji_039.png"},{"id":"040","tag":"[nm]","file":"emoji_040.png"},{"id":"041","tag":"[dy]","file":"emoji_041.png"},{"id":"042","tag":"[kz]","file":"emoji_042.png"},{"id":"043","tag":"[xy]","file":"emoji_043.png"},{"id":"044","tag":"[ll]","file":"emoji_044.png"},{"id":"045","tag":"[heng]","file":"emoji_045.png"},{"id":"046","tag":"[zk]","file":"emoji_046.png"},{"id":"047","tag":"[qian]","file":"emoji_047.png"},{"id":"048","tag":"[sbing]","file":"emoji_048.png"},{"id":"049","tag":"[shuai]","file":"emoji_049.png"},{"id":"050","tag":"[nu]","file":"emoji_050.png"},{"id":"051","tag":"[atm]","file":"emoji_051.png"},{"id":"052","tag":"[fdmj]","file":"emoji_052.png"},{"id":"053","tag":"[xm]","file":"emoji_053.png"},{"id":"054","tag":"[zhu]","file":"emoji_054.png"},{"id":"055","tag":"[mao]","file":"emoji_055.png"},{"id":"056","tag":"[zy]","file":"emoji_056.png"},{"id":"057","tag":"[fz]","file":"emoji_057.png"},{"id":"058","tag":"[nan]","file":"emoji_058.png"},{"id":"059","tag":"[nv]","file":"emoji_059.png"},{"id":"060","tag":"[yang]","file":"emoji_060.png"},{"id":"061","tag":"[qiqiu]","file":"emoji_061.png"},{"id":"062","tag":"[tz]","file":"emoji_062.png"}]
     */

    private int groupId;
    private String groupIcon;
    private int groupSize;
    private List<EmojiBean> emoji;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public List<EmojiBean> getEmoji() {
        return emoji;
    }

    public void setEmoji(List<EmojiBean> emoji) {
        this.emoji = emoji;
    }

    public static class EmojiBean {
        /**
         * id : 001
         * tag : [xx]
         * file : emoji_001.png
         */

        private String id;
        private String tag;
        private String file;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}
