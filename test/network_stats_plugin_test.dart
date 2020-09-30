import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:network_stats_plugin/network_stats_plugin.dart';

void main() {
  const MethodChannel channel = MethodChannel('network_stats_plugin');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await NetworkStatsPlugin.platformVersion, '42');
  });
}
