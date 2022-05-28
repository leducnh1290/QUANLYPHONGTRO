package com.leducanh.phongtro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;

public class Thongke {
    public void Main(SharedPreferences sharedPreferences, Context context,String index){
        String getroom [];
         int ROOM_DATA_LENGTH = 4;
        String get_data_from_room[];
        LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.thongke_layout, null);
        int nuoc =0,dien=0;
        String c = "",m="",l="",c1="",m1="",l1="";
        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(context,R.style.MaterialAlertDialog_rounded);
        b.setTitle(Html.fromHtml("<b><span style=\"color:red;\"> THỐNG KÊ DÃY "+index+"</span></b>"));
        getroom = sharedPreferences.getString("Room","").split(",");
        TextView textView1 = view.findViewById(R.id.stt);
        TextView textView2 = view.findViewById(R.id.dentail);
        if(!sharedPreferences.getString("Room","").isEmpty()){
            for(int a=0;a<getroom.length;a++){
                get_data_from_room = sharedPreferences.getString(getroom[a],"" ).split(",");
                dien = new Calculator().tongdien(Integer.parseInt(get_data_from_room[1])
                        ,Integer.parseInt(get_data_from_room[0]));
                nuoc = new Calculator().tongnuoc(Integer.parseInt(get_data_from_room[3])
                        ,Integer.parseInt(get_data_from_room[2]));
                 m = new Module().calculator(get_data_from_room,dien,nuoc,a < getroom.length-1 ? true:false,true);
                 m1 = new Module().calculator(get_data_from_room,dien,nuoc,a < getroom.length-1 ? true:false,false);
                 l = "[<span style=\"color:#00FFFF;\">Điện mới: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[0],0)+
                        "</span>, <span style=\"color:yellow;\">Điện cũ: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[1],0)
                         +"</span>, <span style=\"color:#FF00FF;\">Nước mới: "
                        +new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[2],0)+"</span>, <span style=\"color:green;\">" +
                         "Nước cũ: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[3],0)+"</span>]<br>";
                l1 = "[Điện mới: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[0],0)+
                        ",Điện cũ: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[1],0)
                        +",Nước mới: "
                        +new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[2],0)+", Nước cũ: "+new Calculator().RoomDataEdit(ROOM_DATA_LENGTH,get_data_from_room[3],0)+"]\n";
                c+="<b><span style=\"color:red;\">Phòng "+getroom[a]+"</span></b> : "+l+"\n"+m;
                c1+="Phòng "+getroom[a]+" : "+l1+"\n"+m1;
            }
            String finalCopy = "Dãy "+index+"\n Tổng cộng có "+getroom.length +" Phòng\n"
                    +"\n---------- Thống kê chi tiết ------------\n"+c1;
            b.setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    new Clipboard().setClipboard(context, finalCopy);
                    dialog.dismiss();
                    Toast.makeText(context, "Đã copy vào bộ nhớ tạm !", Toast.LENGTH_SHORT).show();
                }
            });
            textView1.setText("Tổng cộng có "+getroom.length +" Phòng");
            textView2.setText(Html.fromHtml(c));
       }else{
            textView1.setText(R.string.not_found_data);
            textView2.setText(R.string.not_found_data);
        }
        b.setView(view);
        b.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }
}
