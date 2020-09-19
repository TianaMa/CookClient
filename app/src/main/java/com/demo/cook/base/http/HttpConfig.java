package com.demo.cook.base.http;

import com.demo.baselib.http.RetrofitUtil;

public class HttpConfig {

    public static final String BASE_URL="http://15i6649s82.iask.in:41009/cook/";

    public static final String FILE_URL="http://117.50.34.176/CGBZ/";

    public static <T>T getHttpServe(final Class <T> httpServe) {
        //Retrofit构建器 通过URL 构建出来的Retrofit对象;Retrofit对象 创建出来网络服务实体类
        return RetrofitUtil.getHttpServe(BASE_URL,httpServe);
    }

}
