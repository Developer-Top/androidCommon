package com.newing.core.base;

import com.google.gson.Gson;
import com.newing.core.entity.ErrorMessage;
import com.newing.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lm on 2017/11/22.
 * Description：自定义gson转换器
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;
    private String msg;
    private int result_code;
    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        LogUtils.e("==type=" + type);
        String[] responsekey = response.split("\"");
        LogUtils.e("responsekey==="+responsekey.toString());
        JSONObject jObj = null;
        JSONObject content = null;
        try {
            jObj = new JSONObject(response);
            LogUtils.e("jObj==="+jObj.toString());
            LogUtils.e("responsekey[0]==="+responsekey[0].toString());
            LogUtils.e("responsekey[1]==="+responsekey[1].toString());
            content = jObj.getJSONObject(responsekey[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (content != null && !"true".equals(content.getString("success"))) {
                //打印服务器信息
                LogUtils.e("success == false!!!!");
                Map map2 = gson.fromJson(content.toString(), Map.class);
                if (map2.containsKey("result_code_msg")) {
                    if (map2.containsKey("msg")) {
                        msg = map2.get("msg").toString();
                    } else {
                        msg = map2.get("result_code_msg").toString();
                    }
                } else if (map2.containsKey("result_message")) {
                    msg = map2.get("result_message").toString();
                } else if (map2.containsKey("sub_msg")) {
                    msg = map2.get("sub_msg").toString();
                }
                result_code =  Integer.parseInt(content.get("result_code").toString());
                if (map2.containsKey("sub_code")) {
                    result_code = Integer.parseInt(content.get("sub_code").toString());
                }
                LogUtils.e("success == false!!!!="+msg);
                LogUtils.e("success == false!!!!="+result_code);
                throw new ErrorMessage(result_code,msg,responsekey[1].toString());
            } else {
                if (type == String.class) {
                    return (T) content.toString();
                } else if (type == Map.class) {
                    Map map = gson.fromJson(content.toString(), Map.class);
                    LogUtils.e("map: " + map);
                    return (T) map;
                }else {
                    return gson.fromJson(content.toString(), type);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
//
    }

    public <T> T transform(String response, Class<T> classOfT) {
        return gson.fromJson(response, classOfT);
    }
}
