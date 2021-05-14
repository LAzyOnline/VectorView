package com.test.testvector.path;

import android.graphics.Path;

/**
 * function:使用圆弧连接Path。执行Path.arcTo操作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathArcTo extends PathRule {

    private static final int SIZE = 7;

    PathArcTo(String key) {
        super(key);
    }

    @Override
    protected void attachToPath(Path path, float density) {
    }

    @Override
    protected int pointSize() {
        return SIZE;
    }
}
