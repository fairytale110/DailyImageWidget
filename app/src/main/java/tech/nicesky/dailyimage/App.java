package tech.nicesky.dailyimage;

import android.app.Application;

import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.OkHttpConnectFactory;
import com.yanzhenjie.kalle.connect.http.LoggerInterceptor;
import com.yanzhenjie.kalle.cookie.DBCookieStore;

import java.util.concurrent.TimeUnit;

import tech.nicesky.dailyimage.http.JsonConverter;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        initKalle();
    }

    /**
     * init http lib
     */
    private void initKalle() {
        KalleConfig config = KalleConfig.newBuilder()
                .addParam("withCredentials","1")
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .connectionTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .connectFactory(OkHttpConnectFactory.newBuilder().build())
                .converter(new JsonConverter(this))
                .addInterceptor(new LoggerInterceptor("HTTP-->", BuildConfig.DEBUG))
                .build();

        Kalle.setConfig(config);
    }
}
