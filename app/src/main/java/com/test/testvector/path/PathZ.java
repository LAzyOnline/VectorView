package com.test.testvector.path;

import android.graphics.Path;

import androidx.annotation.Nullable;

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
    public void attachToPath(@Nullable Path path, float density) {
        if (path == null) {
            return;
        }
        path.close();
    }

    @Override
    protected int pointSize() {
        return 0;
    }
}
