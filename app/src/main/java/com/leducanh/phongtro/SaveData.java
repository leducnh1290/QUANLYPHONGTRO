package com.leducanh.phongtro;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class SaveData {
    public void Save_Data(String room_index, EditText room_input[],SharedPreferences sharedPreferences,Context context,Boolean CHECK_TIEN_DIEN,Boolean CHECK_TIEN_NUOC){
        String data_save = "";
        if(!room_index.isEmpty()){
            for (int a = 0; a < room_input.length; a++) {
                data_save += room_input[a].getText().toString().length() == 0 ? "0" : Integer.parseInt(room_input[a].getText().toString());
                Log.d("1234", data_save);
                if (a < room_input.length -1) {
                    data_save += ",";
                }
            }
            if(!((data_save+","+CHECK_TIEN_DIEN+","+CHECK_TIEN_NUOC).equals(sharedPreferences.getString(room_index,"")))){
                data_save+=","+CHECK_TIEN_DIEN+","+CHECK_TIEN_NUOC;
                Main(room_index, data_save,sharedPreferences,context);
                return;
            }
            Toast.makeText(context, "Không có gì thay đổi", Toast.LENGTH_SHORT).show();
            return;
        }
            Toast.makeText(context, "Chọn Phòng Trước Khi Lưu", Toast.LENGTH_SHORT).show();
    }
    public void Main(String room, String data, SharedPreferences sharedPreferences,Context context){
                sharedPreferences.edit().putString(room,data).commit();
        Toast.makeText(context, "Lưu dữ liệu thành công.", Toast.LENGTH_SHORT).show();
    }
}
