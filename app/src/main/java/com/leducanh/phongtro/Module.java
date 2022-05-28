package com.leducanh.phongtro;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

public class Module {
    Locale locale = new Locale("vi", "VN");;
    NumberFormat vi = NumberFormat.getCurrencyInstance(locale);
    public String calculator(String get_data_from_room[],int dien,int nuoc,boolean b,boolean c){
        String m = "<br>";
        for(int i=1;i<=3;i++){
            if(i<3) {
                m += Check(get_data_from_room,i,i==1?dien:nuoc);
                continue;
            }
          //  Log.d("!233",nuoc+"");
            m+="Điện + nước : "+convert((dien>=0?dien:0)+(nuoc>=0?nuoc:0))+"<br>";
            m+="Điện + nước + phòng : "+convert((dien>=0?dien:0)+(nuoc>=0?nuoc:0)+700000);
            m+="<p  style=\"text-align: center;\">"
                    +(Boolean.parseBoolean(get_data_from_room[4])&&Boolean.parseBoolean(get_data_from_room[5])
                    ?"<span style=\"color:green;\">ĐÃ ĐÓNG ĐỦ":
                    !Boolean.parseBoolean(get_data_from_room[4])&&Boolean.parseBoolean(get_data_from_room[5])
                            ?"<span style=\"color:red;\">CHƯA ĐÓNG TIỀN ĐIỆN":
                            Boolean.parseBoolean(get_data_from_room[4])&&!Boolean.parseBoolean(get_data_from_room[5])
                                    ?"<span style=\"color:red;\">CHƯA ĐÓNG TIỀN NƯỚC":"<span style=\"color:red;\">CHƯA ĐÓNG GÌ CẢ")+"</span></p>";
        }
        if(b){
            m+="<p style=\"text-align: center;\">-------------------------------------</p>";
        }
            if(!c){
                m=m.replace("<br>","\n");
                m=m.replace("<p style=\"text-align: center;\">","\n");
                m=m.replace("</p>","\n");
            }
        return m;
    }
    public String Check(String data[],int value,int gt){
        String a = (value==1?"Điện":"Nước")+" : "+(Integer.parseInt(data[value==1?0:2])-Integer.parseInt(data[value==1?1:3]))
                +(value==1?"(Kí)":"(Khối)")+"*"+convert(value==1?4000:10000)+" = "+convert(gt);
     return (gt>=0?a:(value==1?"Điện":"Nước")+" mới nhỏ hơn "+
                (value==1?"điện":"nước")+" cũ")+"<br>";
    }
    public String convert(int a){
        return vi.format(a);
    }
}
