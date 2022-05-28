package com.leducanh.phongtro;

import android.content.SharedPreferences;

public class getRoomCalculator {
    public static String[] calculator(SharedPreferences sharedPreferences){
        String room_current[] = new String[100];
        String room_save[] = new String[0];
        String room_index[] = new String[0];
        String room = sharedPreferences.getString("Room", "");
        String room2_get = "";
        int ikm = 0;
        if (room != null) {
            for (int i = 0; i < room.length(); i++) {
                if (room.charAt(i) == ',') {
                    room_current[ikm] = room2_get;
                    room2_get = "";
                    ikm++;
                } else if (i == room.length() - 1) {
                    room_current[ikm] = room2_get += room.charAt(i);
                } else {
                    room2_get += String.valueOf(room.charAt(i));
                }
            }
            room_save = new String[ikm + 1];
            room_index = new String[ikm + 1];
            for (int i = 0; i <= ikm; i++) {
                room_save[i] = "Phòng "+room_current[i];
                room_index[i] = room_current[i];
            }
        }
        return room_save;
    }
    public static String[] calculator2(SharedPreferences sharedPreferences){
        String room_current[] = new String[100];
        String room_index[] = new String[0];
        String room = sharedPreferences.getString("Room", "");
        String room2_get = "";
        int ikm = 0;
        if (room != null) {
            for (int i = 0; i < room.length(); i++) {
                if (room.charAt(i) == ',') {
                    room_current[ikm] = room2_get;
                    room2_get = "";
                    ikm++;
                } else if (i == room.length() - 1) {
                    room_current[ikm] = room2_get += room.charAt(i);
                } else {
                    room2_get += String.valueOf(room.charAt(i));
                }
            }
            room_index = new String[ikm + 1];
            for (int i = 0; i <= ikm; i++) {
                room_index[i] = room_current[i];
            }
        }
        return room_index;
    }
    public static String Change(String arr[]) {
        String a = "";
        if (arr.length != 0) {
          for(int i =0;i<arr.length;i++){
              a+=arr[i];
              if(i<arr.length-1){
                  a+=",";
              }
          }
        }
            return a;
    }
}
