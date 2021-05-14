package com.test.testvector.path;

import android.graphics.Path;

/**
 * function:Path移动到某个点。执行Path.moveTo操作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathMoveTo extends PathRule {

    private static final int SIZE = 2;

    PathMoveTo(String key) {
        super(key);
    }

    @Override
    protected void attachToPath(Path path, float density) {
        path.moveTo(absPoints.get(0) * density, absPoints.get(1) * density);
    }

    @Override
    protected int pointSize() {
        return SIZE;
    }
}
