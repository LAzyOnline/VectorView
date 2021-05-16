package com.test.testvector;

import android.content.Context;
import android.graphics.Path;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.test.testvector.path.PathFactory;
import com.test.testvector.path.PathRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Console;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.test.testvector", appContext.getPackageName());
    }

    @Test
    public void quadToParseTest() {
        Float[] results = new Float[]{10f, 14f, 12f, 10f};
        String[] paths = new String[]{"M4,10 Q6,6,8,10 T12,10"
                , "M4,10 Q6,6,8,10 Q10,14,12,10"
                , "M4,10 q2,-4,4,0 q2,4,4,0"
                , "M4,10 q2,-4,4,0 t4,0"
                , "M4,10 q2,-4,4,0 T12,10"
                , "M4,10 Q6,6,8,10 t4,0"};
        boolean result = true;
        for (String path : paths) {
            PathRule pathRule = PathFactory.parse(path);
            PathRule lastPath;
            for (lastPath = pathRule; lastPath != null; lastPath = lastPath.next()) {
                System.out.println(lastPath.toString());
            }
            if (lastPath != null) {
                if (!Arrays.equals(lastPath.getAbsPoints().toArray(new Float[0]), results)) {
                    result = false;
                }
            }
            System.out.println("\n");
        }
        assertTrue(result);
    }
}