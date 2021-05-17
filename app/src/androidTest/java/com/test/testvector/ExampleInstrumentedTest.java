package com.test.testvector;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.test.testvector.path.PathFactory;
import com.test.testvector.path.PathRule;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String[] paths;
    private Float[] results;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.test.testvector", appContext.getPackageName());
    }

    @Test
    public void quadToParseTest() {
        results = new Float[]{10f, 14f, 12f, 10f};
        paths = new String[]{"M4,10 Q6,6,8,10 T12,10"
                , "M4,10 Q6,6,8,10 Q10,14,12,10"
                , "M4,10 q2,-4,4,0 q2,4,4,0"
                , "M4,10 q2,-4,4,0 t4,0"
                , "M4,10 q2,-4,4,0 T12,10"
                , "M4,10 Q6,6,8,10 t4,0"};
    }

    @Test
    public void cubicToParseTest() {
        results = new Float[]{12f, 6f, 14f, 14f, 16f, 10f};
        paths = new String[]{"M4,10 C6,6,8,14,10,10 C12,6,14,14,16,10"
                , "M4,10 C6,6,8,14,10,10 S14,14,16,10"
                , "M4,10 c2,-4,4,4,6,0 c2,-4,4,4,6,0"
                , "M4,10 c2,-4,4,4,6,0 s4,4,6,0"
                , "M4,10 c2,-4,4,4,6,0 S14,14,16,10"
                , "M4,10 C6,6,8,14,10,10 s4,4,6,0"};
    }

    @Test
    public void arcToParseTest() {
        results = new Float[]{4f, 6f, 0f, 1f, 1f, 14f, 16f};
        paths = new String[]{"M8,10 a4,6,0,1,1,6,6"
                , "M8,10 A4,6,0,1,1,14,16"};
    }

    @After
    public void pathToParseTest() {
        boolean result = true;
        for (String path : paths) {
            PathRule pathRule = PathFactory.parse(path);
            PathRule lastPath;
            for (lastPath = pathRule; lastPath != null; lastPath = lastPath.next()) {
                System.out.println(lastPath.toString());
                if (!lastPath.hasNext()) {
                    break;
                }
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