package com.leducanh.phongtro;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

public class Calculator {
    public Locale locale = new Locale("vi", "VN");;
    public NumberFormat vi = NumberFormat.getCurrencyInstance(locale);
    public int tongdien (int cu,int moi){
        return (moi-cu)*4000;
    }
    public int tongnuoc (int cu, int moi){
        return (moi-cu)*10000;
    }
    public String tongnuocKQ(int nuoccu,int nuocmoi){
        return "Nước : "+(nuocmoi-nuoccu)+"(khối)*"+vi.format(10000)+" = "+vi.format(tongnuoc(nuoccu,nuocmoi));
    }
    public String tongdienKQ(int diencu,int dienmoi){
        return "Điện : "+(dienmoi-diencu)+"(kí)*"+vi.format(4000)+" = "+vi.format(tongdien(diencu,dienmoi));
    }
    public String tongtien(int tongtien){
        String a="";
     //   Log.d("123","dđ");
        return "Điện + nước = "+vi.format(tongtien)
                +"\n"+"Điện + nước + phòng(700k) = "+vi.format(tongtien+700000);
    }
    public String RoomDataEdit(int length,String room_data,int config){
        if(!room_data.equals("0")){
            if(config==1)return room_data;
            room_data = Integer.parseInt(room_data)+"";
            String new_data_room = "";
            for(int i=0;i<length-room_data.length();i++){
                new_data_room+="0";
            }
            new_data_room+=room_data;
            Log.d("length",new_data_room+"");
            return new_data_room;
        }
        return "0";
    }
}
