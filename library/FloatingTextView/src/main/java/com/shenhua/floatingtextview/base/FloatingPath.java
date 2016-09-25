package com.shenhua.floatingtextview.base;

import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * 路径
 * Created by Shenhua on 9/15/2016.
 */
public class FloatingPath {

    private Path mPath;
    private PathMeasure mPathMeasure;

    protected FloatingPath() {
        this.mPath = new Path();
    }
   
    protected FloatingPath(Path path) {
        this.mPath = path;
    }

    public static FloatingPath create(Path path, boolean forceClose) {
        FloatingPath floatingPath = new FloatingPath(path);
        floatingPath.mPathMeasure = new PathMeasure(path, forceClose);
        return floatingPath;
    }

    public Path getPath() {
        return mPath;
    }

    public PathMeasure getPathMeasure() {
        return mPathMeasure;
    }
}
