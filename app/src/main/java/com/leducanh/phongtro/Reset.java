package com.leducanh.phongtro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class Reset {
    String get_room_name[],get_room_data[],room_data_new[],data_change ="";
    public void Ask(Context context,SharedPreferences[] sharedPreferences) {
        MaterialAlertDialogBuilder a = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_rounded);
        a.setTitle(Html.fromHtml(context.getString(R.string.ok)));
        String charSequence = "Chức năng này sẽ chuyển<b><p style=\"color:red;\">" +
                "• [Số nước mới] → [Số nước cũ]<br>• [Số điện mới] → [Số điện cũ]" +
                "</p></b>Tích chọn vào dãy cần thực hiện";
        a.setMessage(Html.fromHtml(charSequence));
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        CheckBox checkBox = new CheckBox(context);
        CheckBox checkBox2 = new CheckBox(context);
        checkBox.setText("Dãy 1");
        checkBox2.setText("Dãy 2");
        CheckBox[] checkBoxes = {checkBox, checkBox2};
        for (CheckBox i : checkBoxes)
            layout.addView(i);
        a.setView(layout);
        a.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                  Run(sharedPreferences,context,checkBox.isChecked(),checkBox2.isChecked());
            }
        });
        a.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = a.create();
        alertDialog.show();
    }
public void Main(SharedPreferences sharedPreferences,Context context,int c,int k){
    if(!sharedPreferences.getString("Room","").isEmpty()){
        get_room_name = sharedPreferences.getString("Room","").split(",");
        for(int i=0;i<get_room_name.length;i++){
            room_data_new = new String[]{"0", "0", "0", "0","false,false"};
            get_room_data = sharedPreferences.getString(get_room_name[i],"" ).split(",");
            for(int a=0;a<get_room_data.length;a++){
                data_change = "";
                switch (a){
                    case 0:
                        room_data_new[1] = get_room_data[a];
                        break;
                    case 2:
                        room_data_new[3] = get_room_data[a];
                        break;
                }
            }
            for (int b=0;b<room_data_new.length;b++){
                data_change += room_data_new[b];
                if(b<room_data_new.length-1){
                    data_change+=",";
                }
            }
            sharedPreferences.edit().putString(get_room_name[i],data_change ).commit();
        }
        Toast.makeText(context, (k==1?"Reset 2 dãy thành công":"Reset dãy "+c+ " thành công"), Toast.LENGTH_SHORT).show();
        return;
    }
    Toast.makeText(context, "Dãy "+c+ " chưa có dữ liệu", Toast.LENGTH_SHORT).show();
}
    public void Run(SharedPreferences[] sharedPreferences,Context context,boolean day1,boolean day2) {
      if(day1&&!day2){
          Main(sharedPreferences[0], context,1,0);
          if(!sharedPreferences[0].getString("Room","").isEmpty())
              DataIO.triggerRebirth(context);
      }else if(day2&&!day1){
          Main(sharedPreferences[1], context,2,0);
          if(!sharedPreferences[1].getString("Room","").isEmpty())
              DataIO.triggerRebirth(context);
      }else if(day1&&day2){
          for(int l=0;l<sharedPreferences.length;l++){
              Main(sharedPreferences[l], context,(l+1),1);
          }
          if(!sharedPreferences[0].getString("Room","").isEmpty()&&!sharedPreferences[1].getString("Room","").isEmpty())
              DataIO.triggerRebirth(context);

      }
    }
}
