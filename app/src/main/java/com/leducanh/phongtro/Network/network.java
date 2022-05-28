package com.leducanh.phongtro.Network;

import android.content.Context;
import android.net.ConnectivityManager;

public class network {
    Context context;
    public network(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo () != null && cm.getActiveNetworkInfo ().isConnected ();
    }
}
