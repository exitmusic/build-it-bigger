package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kchang on 1/9/16.
 *
 * http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
 */
public class EndpointsAsyncTaskTest extends ApplicationTestCase<Application>{

    String mJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public EndpointsAsyncTaskTest() {
        super(Application.class);
    }

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
        }).execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJoke));

    }
}
