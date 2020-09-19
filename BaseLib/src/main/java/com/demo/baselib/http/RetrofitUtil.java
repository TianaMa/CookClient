package com.demo.baselib.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    //Retrofit 的构建器
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .client(new OkHttpClient().newBuilder()
                    .addInterceptor(new HttpLoggingInterceptor()//OkHttp3 的日志截取器
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(10, TimeUnit.SECONDS)//网络超时时间
                    .readTimeout(20, TimeUnit.SECONDS)//读取超时时间
                    .writeTimeout(20, TimeUnit.SECONDS)//
                    //TODO 其他设置
                    .build())//OkHttp的委托人
            .addConverterFactory(GsonConverterFactory.create());// 添加GSon数据解析器

    public static <T>T getHttpServe(String BaseUrl, final Class <T> httpServe) {
        //Retrofit构建器 通过URL 构建出来的Retrofit对象;Retrofit对象 创建出来网络服务实体类
        return retrofitBuilder.baseUrl(BaseUrl).build().create(httpServe);
    }

}

