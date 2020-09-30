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
    int allRxBytesWifi;
    int allTxBytesWifi;
    int packageRxBytesWifi;
    int packageTxBytesWifi;
    int allRxBytesMobile;

    try {
      allRxBytesWifi = await NetworkStatsPlugin.getAllRxBytesWifi;
      allTxBytesWifi = await NetworkStatsPlugin.getAllTxBytesWifi;
      packageRxBytesWifi = await NetworkStatsPlugin.getPackageRxBytesWifi;
      packageTxBytesWifi = await NetworkStatsPlugin.getPackageTxBytesWifi;
    } on PlatformException catch (e) {
      print(e.message);
    }

    if (!mounted) return;

    setState(() {
      _networkStats =
          'Your allRxBytesWifi: $allRxBytesWifi\nYour allTxBytesWifi: $allTxBytesWifi\nYour packageRxBytesWifi: $packageRxBytesWifi\nYour packageTxBytesWifi: $packageTxBytesWifi';
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
