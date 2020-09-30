import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:network_stats_plugin/network_stats_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _networkStats = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    NetworkStatsPlugin.grantUsagePermission();
    int allRxBytesWifi;
    int allTxBytesWifi;
    int packageRxBytesWifi;
    int packageTxBytesWifi;
    int allRxBytesMobile;
    int allTxBytesMobile;
    int packageRxBytesMobile;
    int packageTxBytesMobile;

    try {
      allRxBytesWifi = await NetworkStatsPlugin.getAllRxBytesWifi;
      allTxBytesWifi = await NetworkStatsPlugin.getAllTxBytesWifi;
      packageRxBytesWifi = await NetworkStatsPlugin.getPackageRxBytesWifi;
      packageTxBytesWifi = await NetworkStatsPlugin.getPackageTxBytesWifi;
      allRxBytesMobile = await NetworkStatsPlugin.getAllRxBytesMobile;
      allTxBytesMobile = await NetworkStatsPlugin.getAllTxBytesMobile;
      packageRxBytesMobile = await NetworkStatsPlugin.getPackageRxBytesMobile;
      packageTxBytesMobile = await NetworkStatsPlugin.getPackageTxBytesMobile;
    } on PlatformException catch (e) {
      print(e.message);
    }

    if (!mounted) return;

    setState(() {
      _networkStats = 'Your allRxBytesWifi: $allRxBytesWifi';
      _networkStats += '\nYour allTxBytesWifi: $allTxBytesWifi';
      _networkStats += '\nYour packageRxBytesWifi: $packageRxBytesWifi';
      _networkStats += '\nYour packageTxBytesWifi: $packageTxBytesWifi';
      _networkStats += '\nYour allRxBytesMobile: $allRxBytesMobile';
      _networkStats += '\nYour allTxBytesMobile: $allTxBytesMobile';
      _networkStats += '\nYour packageRxBytesMobile: $packageRxBytesMobile';
      _networkStats += '\nYour packageTxBytesMobile: $packageTxBytesMobile';
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('$_networkStats'),
        ),
      ),
    );
  }
}
