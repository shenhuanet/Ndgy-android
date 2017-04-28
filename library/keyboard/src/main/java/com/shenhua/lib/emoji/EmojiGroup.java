package com.shenhua.lib.emoji;

/**
 * Created by shenhua on 4/28/2017.
 * Email shenhuanet@126.com
 */
public class EmojiGroup {

    private int groupItem;
    private int groupIcon;
    private int groupFilePath;

    public EmojiGroup(int groupItem, int groupIcon, int groupFilePath) {
        this.groupItem = groupItem;
        this.groupIcon = groupIcon;
        this.groupFilePath = groupFilePath;
    }

    public int getGroupItem() {
        return groupItem;
    }

    public void setGroupItem(int groupItem) {
        this.groupItem = groupItem;
    }

    public int getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(int groupIcon) {
        this.groupIcon = groupIcon;
    }

    public int getGroupFilePath() {
        return groupFilePath;
    }

    public void setGroupFilePath(int groupFilePath) {
        this.groupFilePath = groupFilePath;
    }
}
