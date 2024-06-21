package com.soonphe.timber;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * AndroidUI测试
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

//    @Rule
//    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, true);

    @Test
    public void useAppContext() {
        //验证包名是否正确
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.soonphe.timber-widget.test", appContext.getPackageName());
        //模拟点击事件
//        onView(withId(R.id.fab)).perform(click());
        //启动Activity
//        activityTestRule.getActivity();
    }
}