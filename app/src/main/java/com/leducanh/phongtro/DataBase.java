package com.leducanh.phongtro;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase {
    Context ctx;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference[] = new DatabaseReference[2];
    public DataBase(Context context){
        this.ctx = context;
    }
    public DatabaseReference Data(){
        databaseReference[0] = firebaseDatabase.getReference(GetKeyData());
        return databaseReference[0];
    }
    public DatabaseReference Pass(){
        databaseReference[1] = firebaseDatabase.getReference(GetKey());
        return databaseReference[1];
    }
    static {
        System.loadLibrary("native-lib");
    }

    private native String GetKey();
    private native String GetKeyData();
}
