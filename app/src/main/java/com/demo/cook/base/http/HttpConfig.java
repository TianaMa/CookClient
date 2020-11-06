package com.demo.cook.base.http;

import com.demo.baselib.http.RetrofitUtil;

public class HttpConfig {

    public static final String BASE_URL="http://15i6649s82.iask.in/cook/";

    public static <T>T getHttpServe(final Class <T> httpServe) {
        //Retrofit构建器 通过URL 构建出来的Retrofit对象;Retrofit对象 创建出来网络服务实体类
        return RetrofitUtil.getHttpServe(BASE_URL,httpServe);
    }

}
