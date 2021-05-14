package com.test.testvector.path;

import android.graphics.Path;

/**
 * function:使用线段连接某个点。执行Path.lineTo动作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathLineTo extends PathRule {

    private static final int SIZE = 2;

    PathLineTo(String key) {
        super(key);
    }

    @Override
    protected void attachToPath(Path path, float density) {
        path.lineTo(absPoints.get(0) * density, absPoints.get(1) * density);
    }

    @Override
    protected int pointSize() {
        return SIZE;
    }
}
