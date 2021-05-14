package com.test.testvector.path;

import android.graphics.Path;

/**
 * function:关闭Path。执行Path.close()操作
 *
 * @author fengrenjie
 * modify date: 2021/5/13
 */
public class PathZ extends PathRule {

    PathZ(String key) {
        super(key);
    }

    @Override
    protected void attachToPath(Path path, float density) {
        path.close();
    }

    @Override
    protected int pointSize() {
        return 0;
    }
}
