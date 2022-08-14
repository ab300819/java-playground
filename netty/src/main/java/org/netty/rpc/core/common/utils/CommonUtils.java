package org.netty.rpc.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mengshen
 */
public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public static String getLocalIP() {

//        try {
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//            InetAddress ip = null;
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
//                    continue;
//                } else {
//                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
//                    while (addresses.hasMoreElements()) {
//                        ip = addresses.nextElement();
//                        if (ip != null && ip instanceof Inet4Address) {
//                            return ip.getHostAddress();
//                        }
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            log.error("fail to get ip address!");
//        }

        return "127.0.0.1";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}
