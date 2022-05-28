package com.leducanh.phongtro;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;

public class LoadData {
    public String[] Load(String room_index,int length){
        String[] data_get = new String[length];
        String id = "";
        int ikm = 0;
        String room_data = room_index;
        for (int a = 0; a < room_data.length(); a++) {
            if (room_data.charAt(a) == ',') {
                data_get[ikm] = id;
                id = "";
                ikm++;
            } else if (a == room_data.length() - 1) {
                data_get[ikm] = id += room_data.charAt(a);
            } else {
                id += String.valueOf(room_data.charAt(a));
            }
        }
        Log.d("data", Arrays.toString(data_get));
        return data_get;
    }
}
