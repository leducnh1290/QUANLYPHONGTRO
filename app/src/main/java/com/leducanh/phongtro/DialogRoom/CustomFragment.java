package com.leducanh.phongtro.DialogRoom;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.leducanh.phongtro.DataIO;
import com.leducanh.phongtro.Options.Options;
import com.leducanh.phongtro.R;


/**
 * Created by Le duc anh on 21/01/2017.
 */

public class CustomFragment extends Fragment {
    EditText editText;
    Button button;
    Dialog dialog;
    DatabaseReference databaseReference;
    Options options;
    String msg = "null";
    Context context;
    private SharedPreferences[] msharedPreferences;
    int a = 0;
    public CustomFragment createInstance(SharedPreferences[] sharedPreferences, Dialog dialog, Context context, DatabaseReference databaseReference, Options options,int a,String msg)
    {
        this.dialog = dialog;
        this.msharedPreferences = sharedPreferences;
        this.context = context;
        this.msg = msg;
        this.databaseReference = databaseReference;
        this.options = options;
        this.a = a;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sample,container,false);
        TextView textView = v.findViewById(R.id.textView);
        textView.setText(msg);
        editText = v.findViewById(R.id.room);
        button = v.findViewById(R.id.save);
        button.setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    button.setEnabled(true);
                }else{
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               switch (a){
                   case 0:
                       new DataIO(options,databaseReference,context).start_save(msharedPreferences,context,editText.getText().toString(),options);
                       break;
                   case 1:
                       new DataIO(options,databaseReference,context).start_load(msharedPreferences,context,editText.getText().toString(),true,options);
                       break;
               }
                dialog.dismiss();
            }
        });
        return v;
    }
}
