import 'dart:async';

import 'package:flutter/services.dart';

class NetworkStatsPlugin {
  static const MethodChannel _channel =
      const MethodChannel('network_stats_plugin');

  static Future<bool> checkUsagePermission() async {
    final bool data = await _channel.invokeMethod('isUsagePermission');
    return data;
  }

  static Future<void> grantUsagePermission() async {
    await _channel.invokeMethod('grantUsagePermission');
  }

  static Future<int> get getAllRxBytesWifi async {
    final int data = await _channel.invokeMethod('getAllRxBytesWifi');
    return data;
  }

  static Future<int> get getAllTxBytesWifi async {
    final int data = await _channel.invokeMethod('getAllTxBytesWifi');
    return data;
  }

  static Future<int> get getPackageRxBytesWifi async {
    final int data = await _channel.invokeMethod('getPackageRxBytesWifi');
    return data;
  }

  static Future<int> get getPackageTxBytesWifi async {
    final int data = await _channel.invokeMethod('getPackageTxBytesWifi');
    return data;
  }

  static Future<int> get getAllRxBytesMobile async {
    final int data = await _channel.invokeMethod('getAllRxBytesMobile');
    return data;
  }

  static Future<int> get getAllTxBytesMobile async {
    final int data = await _channel.invokeMethod('getAllTxBytesMobile');
    return data;
  }

  static Future<int> get getPackageRxBytesMobile async {
    final int data = await _channel.invokeMethod('getPackageRxBytesMobile');
    return data;
  }

  static Future<int> get getPackageTxBytesMobile async {
    final int data = await _channel.invokeMethod('getPackageTxBytesMobile');
    return data;
  }
}
