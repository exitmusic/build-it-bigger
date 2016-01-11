package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.mock.MockContext;
import android.text.TextUtils;
import android.util.Pair;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kchang on 1/9/16.
 *
 * http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase {

    String mJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;
    MockContext mContext = new MockContext();

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJokeTask() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                mJoke = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute(new Pair<Context, String>(mContext, "getJoke"));
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));

    }
}
