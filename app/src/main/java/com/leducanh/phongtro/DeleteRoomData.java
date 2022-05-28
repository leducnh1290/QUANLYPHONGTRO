package com.leducanh.phongtro;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class DeleteRoomData {
    public  void removed(String a[],String[] b, SharedPreferences sharedPreferences){
        int length1=a.length, length2=b.length;
        int count = 0;
        for(int i = 0; i < length1; i++) {
            count = 0;
            for(int j = 0; j < length2; j++) {
                if(a[i].equals(b[j]))
                    break;
                count++;
            }
            if(count == length2) {
                sharedPreferences.edit().remove(a[i]).commit();
            }
        }
    }
    public  String[] remove(String  arr[], int n){
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(Arrays.asList(arr));
        arr = hashSet.toArray(new String[0]);
        Log.d("1234",Arrays.toString(arr));
        return arr;
    }
    public static String[] sortASC(String [] arr1) {
        int arr[] = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr[i] = Integer.parseInt(arr1[i]);
        }
        int temp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = String.valueOf(arr[i]);
        }
        return arr1;
       }
    }
