package com.katgo.network_stats_plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import androidx.core.content.ContextCompat;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.annotation.TargetApi;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.app.AppOpsManager;
import android.net.ConnectivityManager;
import android.os.RemoteException;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.os.Process;
import android.os.Bundle;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** NetworkStatsPlugin */
public class NetworkStatsPlugin implements MethodCallHandler {
  private final Activity activity;

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "network_stats_plugin");
    channel.setMethodCallHandler(new NetworkStatsPlugin(registrar.activity()));
  }

  private NetworkStatsPlugin(Activity activity) {
      this.activity = activity;
  }

  @SuppressLint("HardwareIds")
  @Override
  public void onMethodCall(MethodCall call, Result result) {
    TelephonyManager manager = (TelephonyManager) activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
    NetworkStatsManager networkStatsManager = (NetworkStatsManager) activity.getApplicationContext().getSystemService(Context.NETWORK_STATS_SERVICE);
    PackageManager pm = activity.getApplicationContext().getPackageManager();
    switch (call.method) {
        case "isUsagePermission": {
            String packageName = activity.getApplicationContext().getPackageName();
            AppOpsManager appOps = (AppOpsManager) activity.getApplicationContext().getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName);
            if (mode == AppOpsManager.MODE_ALLOWED) {
                result.success(true); 
            }
            result.success(false);
        }
        case "grantUsagePermission": {
            String packageName = activity.getApplicationContext().getPackageName();
            AppOpsManager appOps = (AppOpsManager) activity.getApplicationContext().getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName);
            if (mode != AppOpsManager.MODE_ALLOWED) {
                Intent launchIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                if (launchIntent != null) {
                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                activity.getApplicationContext().startActivity(launchIntent); 
            }
        }
        case "getAllRxBytesWifi": {
            NetworkStats.Bucket bucket;
            try {
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
                        "",
                        0,
                        System.currentTimeMillis());
                result.success(bucket.getRxBytes());
            } catch (RemoteException e) {
                result.error("1", "Error getting getAllRxBytesWifi", "");
            }
            break;
        }
        case "getAllTxBytesWifi": {
            NetworkStats.Bucket bucket;
            try {
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_WIFI,
                        "",
                        0,
                        System.currentTimeMillis());
                result.success(bucket.getRxBytes());
            } catch (RemoteException e) {
                result.error("1", "Error getting getAllTxBytesWifi", "");
            }
            break;
        }
        case "getPackageRxBytesWifi": {
            NetworkStats networkStats = null;
            try {
                int uid = -1;
                String packageName = activity.getApplicationContext().getPackageName();
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
                uid = packageInfo.applicationInfo.uid;
                networkStats = networkStatsManager.queryDetailsForUid(
                            ConnectivityManager.TYPE_WIFI,
                            "",
                            0,
                            System.currentTimeMillis(),
                            uid);
                long rxBytes = 0L;
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (networkStats.hasNextBucket()) {
                    networkStats.getNextBucket(bucket);
                    rxBytes += bucket.getRxBytes();
                }
                networkStats.close();

                result.success(rxBytes);
            } catch (Exception  e) {
                result.error("1", "Error getting getAllTxBytesWifi", "");
            }
            break;
        }
        case "getPackageTxBytesWifi": {
            NetworkStats networkStats = null;
            try {
                int uid = -1;
                String packageName = activity.getApplicationContext().getPackageName();
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
                uid = packageInfo.applicationInfo.uid;
                networkStats = networkStatsManager.queryDetailsForUid(
                            ConnectivityManager.TYPE_WIFI,
                            "",
                            0,
                            System.currentTimeMillis(),
                            uid);
                long txBytes = 0L;
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (networkStats.hasNextBucket()) {
                    networkStats.getNextBucket(bucket);
                    txBytes += bucket.getTxBytes();
                }
                networkStats.close();

                result.success(txBytes);
            } catch (Exception  e) {
                result.error("1", "Error getting getAllTxBytesWifi", "");
            }
            break;
        }
        case "getAllRxBytesMobile": {
            NetworkStats.Bucket bucket;
            try {
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                        manager.getSubscriberId(),
                        0,
                        System.currentTimeMillis());
                result.success(bucket.getRxBytes());
            } catch (RemoteException e) {
                result.error("1", "Error getAllRxBytesMobile", "");
            }
            break;
        }
        case "getAllTxBytesMobile": {
            NetworkStats.Bucket bucket;
            try {
                bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE,
                        manager.getSubscriberId(),
                        0,
                        System.currentTimeMillis());
                result.success(bucket.getTxBytes());
            } catch (RemoteException e) {
                result.error("1", "Error getAllTxBytesMobile", "");
            }
            break;
        }
        case "getPackageRxBytesMobile": {
            NetworkStats networkStats = null;
            try {
                int uid = -1;
                String packageName = activity.getApplicationContext().getPackageName();
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
                uid = packageInfo.applicationInfo.uid;
                networkStats = networkStatsManager.queryDetailsForUid(
                        ConnectivityManager.TYPE_MOBILE,
                        manager.getSubscriberId(),
                        0,
                        System.currentTimeMillis(),
                        uid);
                long rxBytes = 0L;
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (networkStats.hasNextBucket()) {
                    networkStats.getNextBucket(bucket);
                    rxBytes += bucket.getRxBytes();
                }
                networkStats.close();

                result.success(rxBytes);
            } catch (Exception e) {
                result.error("1", "Error getting getPackageRxBytesMobile", "");
            }
            break;
        }
        case "getPackageTxBytesMobile": {
            NetworkStats networkStats = null;
            try {
                int uid = -1;
                String packageName = activity.getApplicationContext().getPackageName();
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
                uid = packageInfo.applicationInfo.uid;
                networkStats = networkStatsManager.queryDetailsForUid(
                        ConnectivityManager.TYPE_MOBILE,
                        manager.getSubscriberId(),
                        0,
                        System.currentTimeMillis(),
                        uid);
                long txBytes = 0L;
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (networkStats.hasNextBucket()) {
                    networkStats.getNextBucket(bucket);
                    txBytes += bucket.getTxBytes();
                }
                networkStats.close();

                result.success(txBytes);
            } catch (Exception e) {
                result.error("1", "Error getting getPackageTxBytesMobile", "");
            }
            break;
        }
        default:
            result.notImplemented();
            break;
    }
  }
}
