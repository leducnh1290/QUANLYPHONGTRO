package com.leducanh.phongtro.calculatorSum;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.leducanh.phongtro.Calculator;
import com.leducanh.phongtro.Module;
import com.leducanh.phongtro.R;

import java.util.Arrays;

public class DialogStatistic {
    MaterialAlertDialogBuilder a;
    LinearLayout ln;
    int count[] = new int[3];
    String[] style = {"<span style=\"color:green;\">","<span style=\"color:#00FFFF;\">",
            "<p style=\"text-align: center;\">------------------------</p>","<span style=\"color:yellow;\">"};
    String text = "";
    TextView tv;
    LinearLayout.LayoutParams lp = new
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
            ,LinearLayout.LayoutParams.WRAP_CONTENT);
    public void Show(Context context, SharedPreferences[] dataSave){
        InitView(context);
        setupView(context,dataSave);
        DialogBuider(context);
    }

    private void setupView(Context context,SharedPreferences[] dataSave) {
        tv.setText("Chưa có dữ liệu !");
        for(int l=0;l<dataSave.length;l++){
            if(!dataSave[l].getString(context.getString(R.string.Room),"").isEmpty()) {
                    String[] dataRoom = dataSave[l].getString(context.getString(R.string.Room), "")
                            .split(",");
                    String[] data;
                    int[] sum_current = new int[2];
                    int[] sum = new int[2];
                    for (int i = 0; i < dataRoom.length; i++) {
                        data = dataSave[l].getString(dataRoom[i], "").split(",");
                        sum_current[0] = new Calculator().tongdien(
                                Integer.parseInt(data[1])
                                , Integer.parseInt(data[0])
                        );
                        sum_current[1] = new Calculator().tongnuoc(
                                Integer.parseInt(data[3])
                                , Integer.parseInt(data[2])
                        );
                        //  Log.d("room day"+(n+1), Arrays.toString(data));
                        //  Log.d("getdata",sum_current[0]+"\n");
                        sum[0] += sum_current[0] > 0 ? sum_current[0] : 0;
                        sum[1] += sum_current[1] > 0 ? sum_current[1] : 0;
                        // get data room and add to array
                    }
                    count[0] += sum[0];
                    count[1] += sum[1];
                    count[2] += sum[0] + sum[1];
                    text+= "<b>" + (l == 0 ? style[0] : style[1]) + "Tổng điện dãy " + (l + 1) + " :</span> "
                            + new Module().convert(sum[0])
                            + "<p>" + (l == 0 ? style[0] : style[1]) + "Tổng nước dãy " + (l + 1) + " :</span> "
                            + new Module().convert(sum[1])
                            + "<p>" + (l == 0 ? style[0] : style[1]) + "Tổng dãy " + (l + 1) + " :</span> " +
                            new Module().convert(sum[1] + sum[0]) + (l == 0 ? style[2] : "") +
                            (l == 0 ? "" : style[2]+(l!=0||l!=1 ? style[3]:"")+"Tổng điện cả 2 dãy : </span>"+new Module().convert(count[0])
                    +"<p>"+ (l!=0||l!=1 ? style[3]:"")+"Tổng nước cả 2 dãy :</span> "
                                    +new Module().convert(count[1])+ "<p>"+(l!=0||l!=1 ? style[3]:"")+"Tổng 2 dãy :</span> "
                                    + new Module().convert(count[2])) + "</b>";
                }
            tv.setText(Html.fromHtml(text));;
        }
        ln.addView(tv);

    }
private void InitView(Context ctx){
        lp.setMargins(20,20,20,20);
    ln = new LinearLayout(ctx);
    tv = new TextView(ctx);
    tv.setLayoutParams(lp);
}
    private void DialogBuider(Context context) {
        a = new MaterialAlertDialogBuilder(context)
                .setTitle(Html.fromHtml("<b>"+context.getString(R.string.thongke)+" cả 2 dãy </b>"));
        a.setView(ln);
        a.create().show();

    }
}
