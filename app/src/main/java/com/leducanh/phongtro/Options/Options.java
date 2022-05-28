package com.leducanh.phongtro.Options;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leducanh.phongtro.DataBase;
import com.leducanh.phongtro.R;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Options {
    SecretKeySpec skeySpec;
    String key;
    DatabaseReference myRef;
    Cipher cipher;
    public Options(DatabaseReference databaseReference) {
        myRef = databaseReference;
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                    Options.this.key = snapshot.getValue().toString();
                    skeySpec = new SecretKeySpec(key.getBytes(), "AES");
                        cipher = Cipher.getInstance("AES");
                    }catch (Exception E){
                        Log.d("bug",E.toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
    public String Encrypt(String message,Context c){
        String encrypted ="";
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] byteEncrypted = cipher.doFinal(message.getBytes());
          encrypted =  Base64.encodeToString(byteEncrypted,1);
        }catch (Exception E){
            Log.d("1",E.toString());
        }
      return encrypted;
    }
public String Decrypt(String message,Context context) {
    String decrypted = "";
    try {
        SecretKeySpec spec = skeySpec;
        cipher.init(Cipher.DECRYPT_MODE, spec);
        byte[] byteDecrypted = cipher.doFinal(Base64.decode(message, 1));
        decrypted = new String(byteDecrypted);
    } catch (Exception E) {
       Log.e("123",E.toString());
    }
    return decrypted;
     }
}
