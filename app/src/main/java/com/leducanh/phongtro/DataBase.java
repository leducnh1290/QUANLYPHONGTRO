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
        databaseReference[0] = firebaseDatabase.getReference(ctx.getString(R.string.data));
        return databaseReference[0];
    }
    public DatabaseReference Pass(){
        databaseReference[1] = firebaseDatabase.getReference(ctx.getString(R.string.pass));
        return databaseReference[1];
    }
}
