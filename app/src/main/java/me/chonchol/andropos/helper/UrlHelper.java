package me.chonchol.andropos.helper;

/**
 * Created by mehedi.chonchol on 24-Oct-18.
 */

public final class UrlHelper {

    private static UrlHelper urlHelper = new UrlHelper();

    private String url;
    private String port;

    public UrlHelper() {

    }

    public UrlHelper(String url, String port){
        this.url = url;
        this.port = port;
    }

    public static UrlHelper getInstance()
    {
        if (urlHelper == null)
            urlHelper = new UrlHelper();
        return urlHelper;
    }

    public String getUrl() {
        return url;
    }

    public String getPort() {
        return port;
    }

    public String getFullUrl(){
        return urlHelper.getUrl()+":"+urlHelper.getPort();
    }
}
