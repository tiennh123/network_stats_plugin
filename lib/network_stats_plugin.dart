import 'dart:async';

import 'package:flutter/services.dart';

class NetworkStatsPlugin {
  static const MethodChannel _channel =
      const MethodChannel('network_stats_plugin');

  static Future<int> get getAllRxBytesMobile async {
    final int meid = await _channel.invokeMethod('getAllRxBytesMobile');
    return meid;
  }

  static Future<int> get getAllRxBytesWifi async {
    final int meid = await _channel.invokeMethod('getAllRxBytesWifi');
    return meid;
  }

  static Future<int> get getAllTxBytesWifi async {
    final int meid = await _channel.invokeMethod('getAllTxBytesWifi');
    return meid;
  }

  static Future<int> get getPackageRxBytesWifi async {
    final int meid = await _channel.invokeMethod('getPackageRxBytesWifi');
    return meid;
  }

  static Future<int> get getPackageTxBytesWifi async {
    final int meid = await _channel.invokeMethod('getPackageTxBytesWifi');
    return meid;
  }
}
