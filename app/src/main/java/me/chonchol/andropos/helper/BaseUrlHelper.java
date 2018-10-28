package me.chonchol.andropos.helper;

/**
 * Created by mehedi.chonchol on 25-Oct-18.
 */

public class BaseUrlHelper {
    static String clientUrl;
    static String clientPort;

    static String fullUrl = clientUrl+":"+clientPort;

    public static String getClientUrl() {
        return clientUrl;
    }

    public static void setClientUrl(String clientUrl) {
        BaseUrlHelper.clientUrl = clientUrl;
    }

    public static String getClientPort() {
        return clientPort;
    }

    public static void setClientPort(String clientPort) {
        BaseUrlHelper.clientPort = clientPort;
    }
}
