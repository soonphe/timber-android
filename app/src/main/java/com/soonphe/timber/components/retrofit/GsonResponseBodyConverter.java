package com.soonphe.timber.components.retrofit;

import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.soonphe.timber.constants.CodeEnum;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) {
        try {
            String body = value.string();
            JSONObject json = new JSONObject(body);
            String resultCode = json.optString("code");
            if (CodeEnum.SUCCESS.getCode().equals(resultCode)) {
                Object data = json.opt("data");
                if (data == null || data.toString().equals("null")) {
                    return (T) new Object();
                }
                return (T)adapter.fromJson(data.toString());
            } else {
                Log.e("gsonConverter","resultCode not equals 200,msg-" + json.toString());
                String msg = json.optString("message", "");
                throw new RuntimeException(msg);
            }
        } catch (Exception e) {
            Log.e("gsonConverter","Exception,msg-" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }

    /**
     * 验证字符串是否为json格式
     *
     * @param json 字符串
     * @return
     */
    private static boolean isJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            Log.e("gsonConverter","bad json,msg-" + e.getMessage());
            return false;
        }
    }
}
