package com.leducanh.phongtro.loading;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Network;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leducanh.phongtro.Network.network;
import com.leducanh.phongtro.R;

import java.nio.charset.StandardCharsets;

public class Loading {
    AlertDialog alertDialog;
    View view;
    LayoutInflater inflater;
    AlertDialog.Builder al;
    public Loading(Context ctx){
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.loading_upload,null);
        al = new AlertDialog.Builder(ctx);
        al.setCancelable(false);
        al.setView(view);
        alertDialog = al.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public void Loading(String stt) {
        ((TextView) view.findViewById(R.id.loading_text)).setText(stt);
        alertDialog.show();
    }
    public void dimiss(){
        alertDialog.dismiss();
    }
}
