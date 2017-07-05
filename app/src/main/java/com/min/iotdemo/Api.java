package com.min.iotdemo;

import com.min.iotdemo.bean.LoginRequest;
import com.min.iotdemo.bean.LoginResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/12/6
 * Time: 11:30
 * FIXME
 */
public interface Api {
    @GET("v2/movie/top250")
    Observable<LoginResponse> login(@Query("userName") String username,@Query("pwd") String pwd);
    @GET("v2/movie/top2511")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
    @POST("main")
    Observable<Response<LoginRequest>> getMain(@Query("page") String page);
}
