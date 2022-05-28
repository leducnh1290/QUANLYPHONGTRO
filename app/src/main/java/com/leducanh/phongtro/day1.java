package com.leducanh.phongtro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leducanh.phongtro.DialogRoom.TabbedDialog;
import com.leducanh.phongtro.Options.Options;
import com.leducanh.phongtro.calculatorSum.DialogStatistic;

import java.util.Arrays;

public class day1 extends Fragment {
    String room_save[];
    String rom_index_result[];
   public getRoomCalculator getRoomCalculator = new getRoomCalculator();
    ArrayAdapter<String> arrayAdapter;
   TextView author;
   CheckBox cb[] = new CheckBox[2];
   DataBase database;
    String[] save_file;
   DeleteRoomData deleteRoomData = new DeleteRoomData();
    TextInputEditText[] room_input = new TextInputEditText[4];
    String room_index = "";
    View view;
    Options options;
    int nuoc_cu = 0, nuoc_moi = 0, dien_cu = 0, dien_moi = 0, tong_nuoc = 0, tong_dien = 0;
    SharedPreferences sharedPreferences[] = new SharedPreferences[2];
    SharedPreferences.Editor editor;
    TextView[] result = new TextView[4];
    Button button;
    AutoCompleteTextView autoCompleteTextView;
    Calculator calculator = new Calculator();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate (R.layout.day1,container,false);
        initView(getActivity());
        cb[0].setEnabled(false);
        button.setEnabled(false);
        cb[1].setEnabled(false);
        if(sharedPreferences[0].getString("Room","").length() !=0) {
            getRoom();
        }else{
            Toast.makeText(getContext(),
                    "Chưa có dữ liệu về phòng trọ.Hãy thêm phòng trọ",
                    Toast.LENGTH_SHORT).show();
        }
        setHasOptionsMenu(true);
        Event();
        AuthorSet();
        return view;
    }
    public void AuthorSet() {
        author.setText(Html.fromHtml(getStyledText()));
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFacebookIntent("100042271694429");
            }
        });
    }
    @NonNull
    private String getStyledText() {
        return getString1();
    }

    @NonNull
    private String getString1() {
        return getString2();
    }
    @NonNull
    private String getString2() {
        return "<b><i>Thiết kế và lập trình bởi <a href=\"http://fb.com/leducanh1290\">Lê Đức Anh</color></i></b>";
    }
    public void newFacebookIntent(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" +id));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" +id));
            startActivity(intent);
        }
    }
    public void Event(){
        room_input[0].addTextChangedListener(new Custom(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dien(s);
            }
        });
        room_input[1].addTextChangedListener(new Custom(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dien(s);
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cb[0].setEnabled(true);
                cb[1].setEnabled(true);
                button.setEnabled(true);
                room_index = rom_index_result[i];
                setCheckDongTien(sharedPreferences[0]);
                String data_get[] = sharedPreferences[0].getString(room_index,"").split(",");
                for (int b = 0; b <data_get.length ; b++) {
                    if(b<data_get.length-2) {
                        Log.d(b+"",data_get[b]);
                        room_input[b].setText(new Calculator().RoomDataEdit(Default_Values.ROOM_DATA_VALUE_LENGTH, data_get[b],1));
                        continue;
                    }
                    Check(b,data_get[b]);
                }
            }

            private void Check(int index, String result1) {
                switch (index){
                    case 4:
                        cb[0].setChecked(Boolean.parseBoolean(result1));
                        break;
                    case 5:
                        cb[1].setChecked(Boolean.parseBoolean(result1));
                    default:

                }
            }
        });
        room_input[2].addTextChangedListener(new Custom(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nuoc(s);
            }
        });
        room_input[3].addTextChangedListener(new Custom(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nuoc(s);
            }
        });
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences[0].getString("Room","").isEmpty()) {
                    Toast.makeText(getActivity(), "Hãy thêm phòng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveData().Save_Data(room_index,room_input,sharedPreferences[0],getContext(),cb[0].isChecked(),cb[1].isChecked());
                setCheckDongTien(sharedPreferences[0]);
            }
        });
    }
    public void dien(CharSequence s) {
        if(room_input[1].getText().toString().length()==0||room_input[0].getText().length()==0) {
            Test(room_input[1],room_input[0],result[0], "điện");
            tong_dien = 0;
            result[2].setText(calculator.tongtien(tong_dien + tong_nuoc));
        }else {
            dien_cu = Integer.parseInt(room_input[1].getText().toString());
            dien_moi = Integer.parseInt(room_input[0].getText().toString());
            tong_dien = calculator.tongdien(dien_cu, dien_moi);
            result[2].setText(calculator.tongtien((tong_dien>=0?tong_dien:0) + (tong_nuoc>=0?tong_nuoc:0)));
            result[0].setText(tong_dien>=0?calculator.tongdienKQ(dien_cu, dien_moi):"Số điện mới nhỏ hơn số điện cũ.");
        }

    } // tinh toan dien
    public void Test(EditText v1,EditText v2,TextView a,String s){
        if(v1.getText().toString().length()==0&&v2.getText().toString().length()==0) {
            a.setText("Chưa nhập số "+s+" cũ và mới");
        }else{
            a.setText("Chưa nhập số "+s+" "+(v1.getText().toString().length()==0 ? "cũ":"mới"));
        }
    }
    private void setCheckDongTien(SharedPreferences sharedPreferences){
        if(room_index.isEmpty())
            return;
        String b[] = sharedPreferences.getString(room_index,"").split(",");
        if(Boolean.parseBoolean(b[4])&&Boolean.parseBoolean(b[5])) {
            result[3].setText("Đã đóng đủ");
            result[3].setTextColor(Color.GREEN);
            return;
        }
        String a = Boolean.parseBoolean(b[4])&&!Boolean.parseBoolean(b[5])
                ?"tiền nước":!Boolean.parseBoolean(b[4])&&Boolean.parseBoolean(b[5])?"tiền điện":"gì cả.";
        result[3].setText("Chưa đóng "+a);
            result[3].setTextColor(Color.RED);
    }
    public void nuoc(CharSequence s) {
        try {
            if (room_input[3].getText().toString().length() == 0 || room_input[2].getText().toString().length() == 0) {
                Test(room_input[3], room_input[2], result[1], "nước");
                tong_nuoc = 0;
                result[2].setText(calculator.tongtien(tong_dien + tong_nuoc));
            } else {
                nuoc_cu = Integer.parseInt(room_input[3].getText().toString());
                nuoc_moi = Integer.parseInt(room_input[2].getText().toString());
                tong_nuoc = calculator.tongnuoc(nuoc_cu, nuoc_moi);
                result[2].setText(calculator.tongtien((tong_dien>=0?tong_dien:0)+(tong_nuoc>=0?tong_nuoc:0)));
                result[1].setText((tong_nuoc>=0?calculator.tongnuocKQ(nuoc_cu, nuoc_moi):"Số nuớc mới nhỏ hơn số nước cũ."));
            }
        } catch (Exception E) {

        }
    }
    private void initView(Context context) {
        database = new DataBase(context);
        options = new Options(database.Pass());
        int edit_text[] = {R.id.dienmoi, R.id.diencu, R.id.nuocmoi, R.id.nuoccu};
        for (int i = 0; i < edit_text.length; i++) {
            room_input[i] = view.findViewById(edit_text[i]);
        }
        autoCompleteTextView = view.findViewById(R.id.room_selected);
        save_file = new String[]{"room_save_data", "room_save_data2"};
        for(int i=0;i<save_file.length;i++){
            sharedPreferences[i] = context.getSharedPreferences(save_file[i], Context.MODE_PRIVATE);
        }
        editor = sharedPreferences[0].edit();
        int text_view[] = {R.id.tiendien, R.id.tiennuoc, R.id.tientong,R.id.check_tien};
        int cb_init[] = {R.id.check_tiendien,R.id.check_tiennuoc};
        for (int i = 0; i < text_view.length; i++) {
            result[i] = view.findViewById(text_view[i]);
        }
        for (int i = 0; i < cb.length; i++) {
            cb[i] = view.findViewById(cb_init[i]);
        }
        button = view.findViewById(R.id.tinh1);
        author = view.findViewById(R.id.author);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int index = item.getItemId();
        switch (index) {
            case R.id.room:
                RoomAdd(getActivity());
            //   Save(sharedPreferences);
                break;
            case R.id.author:
                new Author().Author(getActivity());
                break;
            case R.id.thongke:
                new Thongke().Main(sharedPreferences[0],getContext(),"1");
                break;
            case R.id.thongkeall:
              new DialogStatistic().Show(getContext(),sharedPreferences);
                break;
            case R.id.reset:
                new Reset().Ask(getContext(),sharedPreferences);
                break;
            case R.id.nhapxuat:
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
               TabbedDialog dialogFragment = new TabbedDialog();
               dialogFragment.TabbedDialogCreate(options,database.Data(),getContext(),sharedPreferences);
               dialogFragment.show(ft,"dialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Save(SharedPreferences[] sharedPreferences) {
        for(SharedPreferences sb:sharedPreferences){
            String room[] = sb.getString("Room","").split(",");
            for(int i=0;i<room.length;i++){
                sb.edit().putString(room[i],sb.getString(room[i],"")+",false").commit();
            }
        }
    }

    private void RoomAdd(Context applicationContext) {
        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(getActivity(),R.style.MaterialAlertDialog_rounded);
        b.setTitle(Html.fromHtml(applicationContext.getString(R.string.ok)));
        b.setMessage(Html.fromHtml(getString(R.string.addroom_note)));
        EditText editText = new EditText(applicationContext);
        editText.setText(sharedPreferences[0]
                .getString("Room",""));
        b.setView(editText);
        b.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(editText.getText().toString().length()!=0){
                    String last_room[] = sharedPreferences[0].getString("Room","").split(",");
                    String room_last[] = deleteRoomData.remove(editText.getText().toString().split(",")
                            ,editText.getText().toString().split(",").length);
                        new DeleteRoomData().removed(last_room,room_last,sharedPreferences[0]);
                 editor.putString("Room", com.leducanh.phongtro.getRoomCalculator.Change(room_last)).commit();
                    for(int i=0;i<room_last.length;i++){
                        if(sharedPreferences[0].getString(room_last[i],"").isEmpty()){
                            editor.putString(room_last[i],"0,0,0,0,"+Default_Values.NEW_ROOM_CHECK_DEFAULT+","+Default_Values.NEW_ROOM_CHECK_DEFAULT).commit();
                        }
                    }
                }
                getRoom();
            }
        });
        b.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }
    public void getRoom() {
           room_save = getRoomCalculator.calculator(sharedPreferences[0]);
           rom_index_result = getRoomCalculator.calculator2(sharedPreferences[0]);
            arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.list_phongtro, room_save);
            autoCompleteTextView.setAdapter(arrayAdapter);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
