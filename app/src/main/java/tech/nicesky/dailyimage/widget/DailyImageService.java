package tech.nicesky.dailyimage.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.simple.SimpleCallback;
import com.yanzhenjie.kalle.simple.SimpleResponse;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import tech.nicesky.dailyimage.Constants;
import tech.nicesky.dailyimage.R;
import tech.nicesky.dailyimage.bean.PoetryData;
import tech.nicesky.dailyimage.util.ChinaDate;

/**
* @class tech.nicesky.dailyimage.widget.DailyImageService
* @date on 2018/10/10-上午8:54
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description: Service for timer
*
*/
public class DailyImageService extends Service {

    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();

        String CHANNEL_ONE_ID = "tech.nicesky.dailyimage";
        String CHANNEL_ONE_NAME = "Channel One";
        NotificationChannel notificationChannel = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID).build();

            startForeground(83674, notification);
            //这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()
            // context.startForeground(SERVICE_ID, builder.getNotification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == mTimer) {
            mTimer = new Timer();
        }
        mTimer.schedule(new MyTimerTask(), 0, 300000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(Service.STOP_FOREGROUND_REMOVE);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class MyTimerTask extends TimerTask {
        DecimalFormat df = new DecimalFormat("#.00");

        @Override
        public void run() {

            Kalle.get(Constants.API_GET_JSON)
                    //.addHeader("withCredentials","true")
                    .perform(new SimpleCallback<PoetryData>() {
                        @Override
                        public void onResponse(SimpleResponse<PoetryData, String> response) {
                            AppWidgetManager widgetManager = AppWidgetManager.getInstance(getApplicationContext());
                            // widgetManager所操作的Widget对应的远程视图即当前Widget的layout文件
                            RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layout_daily_image);
                            // remoteView.setImageViewResource(R.id.time_s, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_sec_%s", s)));
                            // remoteView.setImageViewResource(R.id.time_m, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_min_%s", m)));
                            // remoteView.setImageViewResource(R.id.time_h, ResourceManager.getDrawableResId(getApplicationContext().getPackageName(), String.format("time_hour_%s", h)));

                            // schema 1 使用bitmap旋转
                            Calendar calendar = Calendar.getInstance();
                            int localYear = calendar.get(Calendar.YEAR);
                            int localMonth = calendar.get(Calendar.MONTH);
                            int localDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            int localDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

                            int rawS = calendar.get(Calendar.SECOND);
                            int rawM = calendar.get(Calendar.MINUTE);
                            int rawH = calendar.get(Calendar.HOUR);

                            float realS = rawS;
                            float realM = rawM + realS / 60.0f;
                            float realH = rawH + realM / 60.0f;

                            remoteView.setTextViewText(R.id.txt_date,
                                    localYear + "-" + localMonth + "-" + localDayOfMonth
//                     + " "+ realH + ":"+ realM + ":" + realS
                                    // + "\t\t" + rawH + ":" + rawM
                            );

                            remoteView.setTextViewText(R.id.txt_day_of_month, "" + localDayOfMonth);
                            remoteView.setTextViewText(R.id.txt_week, ChinaDate.getWeek());
                            remoteView.setTextViewText(R.id.txt_date_of_china, ChinaDate.today());


                            double progressData = (localDayOfYear / ChinaDate.getDaysOfYear(localYear)) * 100;

                            remoteView.setTextViewText(R.id.txt_progress_content,
                                    "第" + localDayOfYear + "天,进度已经消耗 " + df.format(progressData) + "%"
                            );
                            remoteView.setProgressBar(R.id.progress_for_day_of_year, 100, (int) progressData, false);
                            if (response.isSucceed()) { // Http成功，业务也成功。
                                PoetryData poetryData = response.succeed();
                                //Log.w("SUCCESS---> ", " " + poetryData.toString());

                                remoteView.setTextViewText(R.id.txt_ancient_poetry_content, poetryData.getContent());
                                remoteView.setTextViewText(R.id.txt_ancient_poetry_title, poetryData.getOrigin().getTitle());
                                remoteView.setTextViewText(R.id.txt_ancient_poetry_author, poetryData.getOrigin().getAuthor());

                            } else {
                                //Toast.show(response.failed());
                                //Log.w("ERROR---> ", " " + response.failed());
                            }

                            ComponentName componentName = new ComponentName(getApplicationContext(), DailyImageProvider.class);
                            ComponentName componentNameLarge = new ComponentName(getApplicationContext(), DailyImageProviderLarge.class);
                            widgetManager.updateAppWidget(componentName, remoteView);
                            widgetManager.updateAppWidget(componentNameLarge, remoteView);
                        }
                    });

            // 当点击Widgets时触发的世界
            // remoteView.setOnClickPendingIntent(viewId, pendingIntent)

        }
    }
}