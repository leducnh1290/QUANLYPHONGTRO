package com.leducanh.phongtro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.Html;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Author {
    public void Author(Context applicationContext) {
        String m = "<span style='color:#FF0000;'>H</span><span style='color:#FF1900;'>ã</span><span style='color:#FF3300;'>y</span> <span style='color:#FF6700;'>C</span><span style='color:#FF8100;'>ứ</span> <span style='color:#FFB500;'>V</span><span style='color:#FFCF00;'>i</span><span style='color:#FFE900;'>ế</span><span style='color:#FAFF00;'>t</span> <span style='color:#C6FF00;'>L</span><span style='color:#ACFF00;'>ê</span><span style='color:#92FF00;'>n</span> <span style='color:#5FFF00;'>N</span><span style='color:#45FF00;'>h</span><span style='color:#2BFF00;'>ữ</span><span style='color:#11FF00;'>n</span><span style='color:#00FF08;'>g</span> <span style='color:#00FF3C;'>D</span><span style='color:#00FF56;'>ò</span><span style='color:#00FF70;'>n</span><span style='color:#00FF8A;'>g</span> <span style='color:#00FFBE;'>C</span><span style='color:#00FFD8;'>o</span><span style='color:#00FFF2;'>d</span><span style='color:#00F2FF;'>e</span> <span style='color:#00BEFF;'>T</span><span style='color:#00A4FF;'>ạ</span><span style='color:#008AFF;'>o</span> <span style='color:#0056FF;'>N</span><span style='color:#003CFF;'>ê</span><span style='color:#0022FF;'>n</span> <span style='color:#1100FF;'>Ư</span><span style='color:#2B00FF;'>ớ</span><span style='color:#4500FF;'>c</span> <span style='color:#7900FF;'>M</span><span style='color:#9200FF;'>ơ</span> <span style='color:#C600FF;'>C</span><span style='color:#E000FF;'>ủ</span><span style='color:#FA00FF;'>a</span> <span style='color:#FF00CF;'>B</span><span style='color:#FF00B5;'>ạ</span><span style='color:#FF009B;'>n</span> <span style='color:#FF0067;'>!</span> <span style='color:#FF0033;'> </span><span style='color:#FF0019;'>😁</span>";
        String a = "<p style=\"color:red;\"><b>Developer by Lê Đức Anh(Cà Chua)" +
                "<br>Email: leducanh1290@gmail.com</p>" +"AppName: "+getAppLable(applicationContext)+"<br>Version: "+ BuildConfig.VERSION_NAME+
                "<p style=\"text-align: center;\">"+m+"</b>";
        MaterialAlertDialogBuilder b = new MaterialAlertDialogBuilder(applicationContext, R.style.MaterialAlertDialog_rounded);
        b.setTitle("Author");
        b.setMessage(Html.fromHtml(a));
        AlertDialog al = b.create();
        al.show();
    }
    public String getAppLable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }
}
