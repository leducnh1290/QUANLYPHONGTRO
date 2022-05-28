package com.leducanh.phongtro;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.leducanh.phongtro.Network.network;
import com.leducanh.phongtro.Options.Options;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leducanh.phongtro.loading.Loading;

import java.util.Arrays;
import java.util.Scanner;

public class DataIO {
    Options options;
    DatabaseReference myRef;
    Loading loading;
    boolean status;
    network network;
    Context ctx;
    public DataIO(Options options_receive,DatabaseReference myRef_receive,Context ctx){
        this.network = new network(ctx);
        this.options = options_receive;
        this.myRef = myRef_receive;
        this.ctx = ctx;
        this.loading = new Loading(ctx);
    }
    public void saveFile(SharedPreferences sharedPreferences[]) {
        String a = "";
        if(sharedPreferences[0].getString(ctx.getString(R.string.Room),"").isEmpty()
                &&sharedPreferences[1].getString(ctx.getString(R.string.Room),"").isEmpty()){
            Toast.makeText(ctx, ctx.getString(R.string.not_found_data), Toast.LENGTH_SHORT).show();
            return;
        }
        Create_Menu(sharedPreferences,ctx,ctx.getString(R.string.save_data),0,true);
    }

    public void readFile(SharedPreferences sharedPreferences[],boolean stt) {
       Create_Menu(sharedPreferences,ctx,ctx.getString(R.string.get_data),1,stt);
    }

    private void Load(String s,SharedPreferences sharedPreferences[]) {
        SharedPreferences sharedPreferences1 = null;
        Scanner scanner = new Scanner(s);
        String line = "";
        try {
            while (scanner.hasNext()){
                line = scanner.nextLine();
                if(line.contains("Room:")||line.contains("Dãy")){
                    if(line.contains("Dãy 1")) {
                        sharedPreferences1 = sharedPreferences[0];
                    }else if(line.contains("Dãy 2")) {
                        sharedPreferences1 = sharedPreferences[1];
                    }else {
                        sharedPreferences1.edit().
                                putString(ctx.getString(R.string.Room),line.replace("Room:",""))
                                .commit();
                    }
                }else{
                    String v[] = line.split(",");
                    //   Log.d("room", Arrays.toString(v));
                    sharedPreferences1.edit().putString(v[0],room_treatment(v)).commit();
                }
            }
            Toast.makeText(ctx, ctx.getString(R.string.import_data_success), Toast.LENGTH_SHORT).show();
            triggerRebirth(ctx);
        }catch (Exception e){
            Log.d("Bug",e.toString());
        }
    }

    public String dentail(SharedPreferences sharedPreferences,String room[]){
        String c = "";
        int l = 0;
          for(String b:room){
              if(b!="") {
                  c += b + "," + sharedPreferences.getString(b, "");
                  if (l < room.length - 1) {
                      c += "\n";
                  }
                  l++;
              }
        }
        return c;
    }
    public String room_treatment(String[] v) {
        String l = "";
        for (int i = 1; i < v.length; i++) {
            l += v[i];
            if (i < v.length - 1) {
                l += ",";
            }
        }
        return l;
    }
    public void Create_Menu(SharedPreferences[] sharedPreferences, Context context,String title,int a,boolean stt) {
        TextInputEditText textInputEditText = new TextInputEditText(context);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context,R.style.MaterialAlertDialog_rounded);
        materialAlertDialogBuilder.setTitle(Html.fromHtml("<b>"+title+"</b>"))
                .setMessage(Html.fromHtml("<b><p style='color:#FF0000;'>Nhập tên bản lưu<p></b>"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (a){
                            case 0:
                                if(!textInputEditText.getText().toString().trim().isEmpty()
                                        &&network.isNetworkConnected()) {
                                    loading.Loading(context.getString(R.string.upload));
                                    start_save(sharedPreferences, context, textInputEditText.getText().toString(),options);
                                }
                                break;
                            case 1:
                                if(!textInputEditText.getText().toString().trim().isEmpty()
                                        &&network.isNetworkConnected()) {
                                    loading.Loading(context.getString(R.string.download));
                                    start_load(sharedPreferences, context, textInputEditText.getText().toString(),stt,options);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
        materialAlertDialogBuilder.setView(textInputEditText);
        AlertDialog materialDialogs = materialAlertDialogBuilder.create();
        materialDialogs.show();
    }

    public void start_load(SharedPreferences[] sharedPreferences, Context context, String pass,boolean stt,Options options) {
        if(network.isNetworkConnected()) {
            loading.Loading(context.getString(R.string.download));
            myRef.child(pass).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String data_read = "";
                    status = stt;
                    try {
                        if (!status) return;
                        loading.dimiss();
                        data_read = options.Decrypt(snapshot.getValue(String.class), context);
                        if (!(data_read.isEmpty())) {
                            for (SharedPreferences sharedPreferences1 : sharedPreferences) {
                                sharedPreferences1.edit().clear().commit();
                            }
                            Log.d("he",data_read);
                            Load(data_read, sharedPreferences);
                            Toast.makeText(context, context.getString(R.string.import_data_success), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, context.getString(R.string.not_found_data_on_cloud), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        status = false;
                        Toast.makeText(context, context.getString(R.string.not_found_data_on_cloud), Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            loading.dimiss();
        }
    }

    public void start_save(SharedPreferences[] sharedPreferences,Context context,String pass,Options options) {
        String a ="";
        loading.Loading(context.getString(R.string.upload));
        if(network.isNetworkConnected()) {
            for(int i=0;i<sharedPreferences.length;i++){
                String room = sharedPreferences[i].getString(context.getString(R.string.Room),"");
                if(room.length()==0) {
                    Toast.makeText(context, "Chưa có dữ liệu dãy "+(i+1), Toast.LENGTH_SHORT).show();
                  continue;
                  }
                a += "Dãy " + (i + 1) + "\n" + (room.isEmpty() ? "" : "Room:" + room) + "\n" +
                        dentail(sharedPreferences[i], sharedPreferences[i].getString(ctx.getString(R.string.Room), "")
                                .split(",")) + "\n";
                Log.d("dđ",a);
                myRef.child(pass).setValue(options.Encrypt(a, context)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        new Clipboard().setClipboard(context, pass);
                        Toast.makeText(context, "Lưu thành công lên server,lưu pass vào bộ nhớ tạm !", Toast.LENGTH_SHORT).show();
                    }
                });
                }
        }
        loading.dimiss();
    }
    public static void triggerRebirth(Context context) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PackageManager packageManager = context.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                ComponentName componentName = intent.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                context.startActivity(mainIntent);
                Runtime.getRuntime().exit(0);
            }
        }, 1000);
    }
}
