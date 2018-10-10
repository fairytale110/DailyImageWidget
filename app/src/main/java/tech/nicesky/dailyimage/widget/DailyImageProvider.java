package tech.nicesky.dailyimage.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import tech.nicesky.dailyimage.R;

/**
* @class tech.nicesky.dailyimage.widget.DailyImageProvider
* @date on 2018/10/10-上午8:53
* @author fairytale110
* @email  fairytale110@foxmail.com
* @description: AppWidgetProvider
*
*/
public class DailyImageProvider extends AppWidgetProvider {

    public DailyImageProvider() {
    }

    public static final String CLICK_ACTION = "com.seewo.appwidgettest.action.CLICK"; // 点击事件的广播ACTION

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_daily_image);
//        Intent intent = new Intent(CLICK_ACTION);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, R.id.doge_imageView, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.doge_imageView, pendingIntent);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

        //Log.w("xxxxx","onUpdate");

    }

    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

//        if (CLICK_ACTION.equals(intent.getAction())) {
//            Toast.makeText(context, "hello dog!", Toast.LENGTH_SHORT).show();
//        }
       // Log.w("xxxxx","onReceive");
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

       // Log.w("xxxxx","onDeleted");
    }

    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context,DailyImageService.class));
       // Log.w("xxxxx","onDisabled");
    }

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        //Log.w("xxxxx","onEnabled");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context,DailyImageService.class));
        } else {
            context.startService(new Intent(context,DailyImageService.class));
        }
    }

    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        //Log.w("xxxxx","onAppWidgetOptionsChanged");
    }

    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
       // Log.w("xxxxx","onRestored");
    }

}
