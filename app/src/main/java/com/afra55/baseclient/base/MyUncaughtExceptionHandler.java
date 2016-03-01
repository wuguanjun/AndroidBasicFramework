package com.afra55.baseclient.base;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.afra55.baseclient.R;
import com.afra55.baseclient.util.Log;

/**
 * Created by yangshuai in the 10:53 of 2016.01.05 .
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "ExceptionHandler";
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Application mApplication;

    public MyUncaughtExceptionHandler(Application context) {
        mApplication = context;
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mApplication.getPackageName().equals(getProcessName(mApplication)))  {

            if (Log.showLog) {
                mUncaughtExceptionHandler.uncaughtException(thread, ex);
            } else {
                toastSorry();
                restart();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void toastSorry() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(
                        mApplication.getApplicationContext(),
                        R.string.app_breakdown,
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
    }

    public static String getProcessName(Context appContext) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }

    /* 重启应用 */
    private void restart() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            android.util.Log.e(TAG, "error : " + e);
        }
        Intent intent = new Intent(
                mApplication.getApplicationContext(),
                MainActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                mApplication.getApplicationContext(), 0, intent,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        //退出程序
        AlarmManager mgr = (AlarmManager) mApplication.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent); // 1秒钟后重启应用

    }
}