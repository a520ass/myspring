package com.hf.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by krt on 2017/4/19.
 */
public class OkHttpTest {

    private  OkHttpClient okHttpClient;

    @Before
    public void before(){
        okHttpClient=new OkHttpClient();
    }

    @Test
    public void testGet() throws IOException {
        Request request = new Request.Builder().url("http://www.baidu.com/").build();
        Response response = okHttpClient.newCall(request).execute();
        String string = response.body().string();
        System.out.print(string);
    }
}
