package tech.nicesky.dailyimage.http;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;

import tech.nicesky.dailyimage.Constants;
import tech.nicesky.dailyimage.util.StringUtils;


/**
* @class tech.nicesky.dailyimage.http.HttpEntity
* @date on 2018/10/10-上午8:51
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description: 网络返回
*
*/
public class HttpEntity implements Parcelable {

    @JSONField(name = "status")
    private String mSucceed;

    @JSONField(name = "errMessage")
    private String mMessage;

    @JSONField(name = "data")
    private String mData;

    @JSONField(name = "errCode")
    private int mErrCode;

    public HttpEntity() {
    }

    protected HttpEntity(Parcel in) {
        mSucceed = in.readString();
        mMessage = in.readString();
        mData = in.readString();
        mErrCode = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSucceed);
        dest.writeString(mMessage);
        dest.writeString(mData);
        dest.writeInt(mErrCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HttpEntity> CREATOR = new Creator<HttpEntity>() {
        @Override
        public HttpEntity createFromParcel(Parcel in) {
            return new HttpEntity(in);
        }

        @Override
        public HttpEntity[] newArray(int size) {
            return new HttpEntity[size];
        }
    };

    public String getStatus() {
        return mSucceed;
    }

    public void setStatus(String mSucceed) {
        this.mSucceed = mSucceed;
    }

    public String getErrMessage() {
        return mMessage;
    }

    public void setErrMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getData() {
        return mData;
    }

    public void setData(String mData) {
        this.mData = mData;
    }

    public int getErrCode() {
        return mErrCode;
    }

    public void setErrCode(int mErrCode) {
        this.mErrCode = mErrCode;
    }

    public boolean isSucceed() {
        if (StringUtils.isEmpty(getStatus())) return false;

        return TextUtils.equals(getStatus(), Constants.STATUS_SUCCESS);
    }
}
