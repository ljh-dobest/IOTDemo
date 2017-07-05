package com.min.iotdemo.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;

/**
 *Created by T-BayMax on 2017/3/13.
 */
public class HttpUtils {
//          public static final String IMAGE_URL ="http://192.168.0.104:90";
//      public static final String URL ="http://192.168.0.104:90/appapi/app";
     public static final String URL = "http://192.168.0.104:90/redis/app";
    public static final String IMAGE_URL = "https://sq.bjike.com";
    private static final String TAG = "HttpClient-Request";




    /**
     * Patch请求
     *
     * @param url
     * @param callback
     */
    public static void sendPatchRequest(String url, StringCallback callback) {
        OkHttpUtils
                .patch()
                .url(URL + url)
                .build()
                .execute(callback);
    }


    /**
     * post提交文件
     *
     * @param url
     * @param file
     * @param callback
     */
    public static void sendFilePostRequest(String url, File file, Callback callback) {
        OkHttpUtils
                .postFile()
                .url(URL + url)
                .file(file)
                .build()
                .execute(callback);
    }

    /**
     * 上传单个文件
     *
     * @param url
     * @param params
     * @param headers
     * @param file
     * @param fileName
     * @param callback
     */
    public static void sendFormatPostRequest(String url, Map<String, String> params,
                                             Map<String, String> headers, File file, String fileName, Callback callback) {

        OkHttpUtils.post()
                .addFile(fileName, file.getName(), file)
                .url(URL + url)
                .params(params)
                .headers(headers)
                .build()
                .execute(callback);
    }

    /**
     * 上传多个文件
     *
     * @param url
     * @param params
     * @param headers
     * @param files
     * @param fileName
     * @param callback
     */
    public static void sendFormatPostRequest(String url, Map<String, String> params,
                                             Map<String, String> headers, Map<String, File> files, String fileName, Callback callback) {
        OkHttpUtils.post()
                .headers(headers)
                .url(URL + url)
                .params(params)
                .files(fileName, files)
                .build()
                .execute(callback);
    }

  public  static void postSingleControl(String url,String name, String status,StringCallback callback ){
      OkHttpUtils.post()
              .addHeader("Connection", "close")
              .url(URL+url)
              .addParams("name",name)
              .addParams("status",status)
              .build()
              .execute(callback);
  }
  public  static void postAllControl(String url,String mainName, String childName,StringCallback callback ){
      OkHttpUtils.post()
              .addHeader("Connection", "close")
              .url(URL+url)
              .addParams("main",mainName)
              .addParams("secondary",childName)
              .build()
              .execute(callback);
  }

}
