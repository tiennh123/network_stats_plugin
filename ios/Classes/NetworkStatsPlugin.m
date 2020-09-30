#import "NetworkStatsPlugin.h"
#if __has_include(<network_stats_plugin/network_stats_plugin-Swift.h>)
#import <network_stats_plugin/network_stats_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "network_stats_plugin-Swift.h"
#endif

@implementation NetworkStatsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNetworkStatsPlugin registerWithRegistrar:registrar];
}
@end
