package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.text.TextUtils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kchang on 1/9/16.
 */
public class EndpointsAsyncTaskTest {

    String mJoke = null;
    Exception mError = null;
    CountDownLatch signal = null;

    @Before
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @After
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    @Test
    public void testEndpointAsyncGetJoke() throws InterruptedException {

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

        Assert.assertNull(mError);
        Assert.assertFalse(TextUtils.isEmpty(mJoke));
        //Assert.assertTrue(TextUtils.isEmpty(mJoke));
    }
}
