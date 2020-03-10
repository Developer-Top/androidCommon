package com.newing.core.rest;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.newing.core.base.GsonConverterFactory;
import com.newing.core.helper.HttpLoggingInterceptor;
import com.newing.utils.LogUtils;
import com.newing.utils.Login;
import com.newing.utils.encrypt.EpayppApiException;
import com.newing.utils.encrypt.EpayppSignature;
import com.newing.utils.encrypt.RSA_Uilt;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.yumei.common.util.security.AESTool;
import cn.yumei.common.util.security.RSATool;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiManager {
    private Retrofit retrofit;
    public static SimpleDateFormat dateFormatSH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static {
        dateFormatSH.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
    public static ApiManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static Retrofit getRestApi() {
        return getInstance().retrofit;
    }

    public void initRest(String url, Interceptor... interceptor) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LoggerFactory.getLogger(ApiManager.class).debug(message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptor.length > 0) {
            for (Interceptor terceptor : interceptor) {
                builder.addInterceptor(terceptor);
            }
        }
        OkHttpClient okHttpClient = builder
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private List<Cookie> session;

                    @Override
                    public void saveFromResponse(@NotNull HttpUrl url, @NotNull List<Cookie> cookies) {
                        session = cookies;
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl url) {
                        return session == null ? new ArrayList<Cookie>() : session;
                    }
                }).addInterceptor(chain -> {//添加公共信息
                    Request originalRequest = chain.request();
                    Map<String, String> rootMap = new HashMap<String, String>();
                    //获取到请求地址api
                    HttpUrl httpUrlurl = originalRequest.url();
                    //通过请求地址(最初始的请求地址)获取到参数列表
                    Set<String> parameterNames = httpUrlurl.queryParameterNames();
                    for (String key : parameterNames) {  //循环参数列表
                        Log.e("parameterNames=" , httpUrlurl.queryParameter(key));
                        if (!key.equals("method")) {
                            rootMap.put(key, httpUrlurl.queryParameter(key));
                        }
                    }
                    String encryptkey = UUID.randomUUID().toString();
                    ObjectInputStream ois = null;
                    String random_key = null;
                    String biz_content = null;
                    try {
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        PublicKey publicKey;
                        publicKey = RSA_Uilt.loadPublicKey(Constants.publicKey);
                        random_key = bytes2HexString(RSATool.encrypt("RSA/ECB/PKCS1Padding", encryptkey.getBytes("UTF-8"), publicKey));
                        Log.e("random_key==",random_key);
                        Gson gson = new Gson();
                        LogUtils.d("appParamsString==" + gson.toJson(rootMap).toString());
                        String dataStr = AESTool.encrypt(gson.toJson(rootMap), encryptkey);
                        biz_content = dataStr;
                        LogUtils.e("biz_content==" + dataStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //添加系统参数,重新组装
                    Map<String, String> rootMap2 = new HashMap<String, String>();
                    String timestamp = dateFormatSH.format(new Date());
                    if (httpUrlurl.queryParameter("method").equals("yumei.personal.query.card.info") || httpUrlurl.queryParameter("method").equals("yumei.personal.query.print.detail")) {
                        rootMap2.put("access_token", Login.mLogin != null ? Login.mLogin.token : "");
                    }
                    rootMap2.put("app_key", "100001");
                    rootMap2.put("format", "json");
                    rootMap2.put("method", httpUrlurl.queryParameter("method"));
                    rootMap2.put("sign_method", "rsa");
                    rootMap2.put("timestamp", timestamp);
                    rootMap2.put("v", "1.1");
                    rootMap2.put("partner_id", "1818001000000818");
                    rootMap2.put("random_key", random_key);
                    rootMap2.put("biz_content", biz_content);
                    // 新的请求+请求头部
                    HttpUrl httpUrl2 = HttpUrl.parse(Constants.SEREVES);
                    if (httpUrlurl.queryParameter("method").equals("yumei.personal.query.card.info") || httpUrlurl.queryParameter("method").equals("yumei.personal.query.print.detail")) {
                        LogUtils.e("222==" + httpUrlurl.queryParameter("method"));
                        HttpUrl.Builder authorizedUrlBuilder = httpUrl2
                                .newBuilder()
                                .scheme(originalRequest.url().scheme())
                                .host(originalRequest.url().host())
                                .addQueryParameter("sign", getKeyStr(rootMap2))
                                .addQueryParameter("access_token", Login.mLogin != null ? Login.mLogin.token : "")
                                .addQueryParameter("app_key", "100001")
                                .addQueryParameter("format", "json")
                                .addQueryParameter("method", httpUrlurl.queryParameter("method"))
                                .addQueryParameter("sign_method", "rsa")
                                .addQueryParameter("timestamp", timestamp)
                                .addQueryParameter("v", "1.1")
                                .addQueryParameter("partner_id", "1818001000000818")
                                .addQueryParameter("random_key", random_key)
                                .addQueryParameter("biz_content", biz_content);
                        Request newRequest = originalRequest.newBuilder()
                                .method(originalRequest.method(), originalRequest.body())
                                .url(authorizedUrlBuilder.build())
                                .build();
                        LogUtils.e("log==" + authorizedUrlBuilder.build().toString());
                        LogUtils.e("log==" + originalRequest.method().toString());
                        LogUtils.e("log==" + originalRequest.body().toString());
                        return chain.proceed(newRequest);
                    } else {
                        LogUtils.e("111==" + httpUrlurl.queryParameter("method"));
                        HttpUrl.Builder authorizedUrlBuilder = httpUrl2
                                .newBuilder()
                                .scheme(originalRequest.url().scheme())
                                .host(originalRequest.url().host())
                                .addQueryParameter("sign", getKeyStr(rootMap2))
                                .addQueryParameter("app_key", "100001")
                                .addQueryParameter("format", "json")
                                .addQueryParameter("method", httpUrlurl.queryParameter("method"))
                                .addQueryParameter("sign_method", "rsa")
                                .addQueryParameter("timestamp", timestamp)
                                .addQueryParameter("v", "1.1")
                                .addQueryParameter("partner_id", "1818001000000818")
                                .addQueryParameter("random_key", random_key)
                                .addQueryParameter("biz_content", biz_content);
                        Request newRequest = originalRequest.newBuilder()
                                .method(originalRequest.method(), originalRequest.body())
                                .url(authorizedUrlBuilder.build())
                                .build();
                        LogUtils.e("log==" + authorizedUrlBuilder.build().toString());
                        LogUtils.e("log==" + originalRequest.method().toString());
                        LogUtils.e("log==" + originalRequest.body().toString());
                        return chain.proceed(newRequest);
                    }


                }).build();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T getApiService(Class<T> service) {
        return retrofit.create(service);
    }


    private static class InstanceHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }




    private static String bytes2HexString(byte[] b) {
        StringBuffer result = new StringBuffer();
        String hex;
        for (int i = 0; i < b.length; i++) {
            hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            result.append(hex.toUpperCase());
        }
        return result.toString();
    }


    //md5签名
    public static String getKeyStr(Map<String, String> params) {
        List<Map.Entry<String, String>> list =
                new ArrayList<Map.Entry<String, String>>(params.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });
        String signStr = "";
        for (Map.Entry<String, String> map : list) {
            signStr = signStr + map.getKey() + map.getValue();
        }
        signStr = signStr + "";//2af2e2f4-c2f7-4eea-ec0a-es0187983646
        String sing = null;
        LogUtils.e("signStr=" + signStr);
        try {
            sing = EpayppSignature.rsaSign(signStr, Constants.privateKey, "UTF-8", "RSA");
        } catch (EpayppApiException e) {
            e.printStackTrace();
        }
//        return SignUtils.md5(signStr);
        return sing;
    }

}
