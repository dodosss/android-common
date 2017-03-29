package com.dodo.android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态
 * 
 * @author <a href="mailto:lhuibo@gmail.com">dodo</a> 2014/02/19
 * @version ${Id}
 */

public class NetworkUtils 
{

    private static final int NETTYPE_WIFI = 0x01;
    private static final int NETTYPE_CMWAP = 0x02;
    private static final int NETTYPE_CMNET = 0x03;

    /**
     * 檢測網絡是否可用
     * 
     * @param context
     * @return true or false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 獲取當前網絡類型
     * 
     * @param context
     * @return 0：沒有網絡   1：WIFI網絡   2：WAP網絡    3：NET網絡
     */
    @SuppressLint("DefaultLocale")
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            boolean isEmpty = true;
            if ( extraInfo != null && !"".equals( extraInfo ) ){
                for ( int i = 0; i < extraInfo.length(); i++ ) {
                    char c = extraInfo.charAt( i );
                    if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' ){
                        isEmpty = false;
                    }
                }
            }
            if(!isEmpty){
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
}