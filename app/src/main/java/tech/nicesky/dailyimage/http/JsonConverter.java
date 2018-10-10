package tech.nicesky.dailyimage.http;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.kalle.Response;
import com.yanzhenjie.kalle.simple.Converter;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.lang.reflect.Type;

import tech.nicesky.dailyimage.R;

/**
* @class tech.nicesky.dailyimage.http.JsonConverter
* @date on 2018/10/10-上午8:52
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description: 解析器
*
*/
public class JsonConverter implements Converter {

    private Context mContext;

    public JsonConverter(Context context) {
        this.mContext = context;
    }

    @Override
    public <S, F> SimpleResponse<S, F> convert(Type succeed, Type failed, Response response, boolean fromCache) throws Exception {
        S succeedData = null; // The data when the business successful.
        F failedData = null; // The data when the business failed.

        int code = response.code();
        String serverJson = response.body().string();
        Log.w("Server Data: ","data===> " + serverJson);
        if (code >= 200 && code < 300) { // Http is successful.
            HttpEntity httpEntity;
            try {
                httpEntity = JSON.parseObject(serverJson, HttpEntity.class);
            } catch (Exception e) {
                httpEntity = new HttpEntity();
                httpEntity.setStatus("error");
                httpEntity.setErrMessage(mContext.getString(R.string.http_server_data_format_error));
            }

            if (httpEntity.isSucceed()) { // The server successfully processed the business.
                try {
                    Log.w(" 解析数据 ==== ", "data--> "+ httpEntity.getData());
                    succeedData = JSON.parseObject(httpEntity.getData(), succeed);
                } catch (Exception e) {
                    failedData = (F) mContext.getString(R.string.http_server_data_format_error);
                }
            } else {
                // The server failed to read the wrong information.
                failedData = (F) httpEntity.getErrMessage();
            }

        } else if (code >= 400 && code < 500) {
            failedData = (F) mContext.getString(R.string.http_unknow_error);
        } else if (code >= 500) {
            failedData = (F) mContext.getString(R.string.http_server_error);
        }

        return SimpleResponse.<S, F>newBuilder()
                .code(response.code())
                .headers(response.headers())
                .fromCache(fromCache)
                .succeed(succeedData)
                .failed(failedData)
                .build();
    }
}
