package com.leducanh.phongtro.DialogRoom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.leducanh.phongtro.Options.Options;
import com.leducanh.phongtro.R;

/**
 * Created by Olakunmi on 21/01/2017.
 */

public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    Options options;
    DatabaseReference databaseReference;
    Context context;
    View rootview;
    SharedPreferences[] sharedPreferences;
    ViewPager viewPager;
public void TabbedDialogCreate(Options options, DatabaseReference databaseReference, Context context, SharedPreferences[] sharedPreferences){
    this.sharedPreferences = sharedPreferences;
    this.databaseReference = databaseReference;
    this.context = context;
    this.options = options;
}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         rootview = inflater.inflate(R.layout.dialog_sample,container,false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);

        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment(getString(R.string.updata_to_server)
                ,new CustomFragment().createInstance(sharedPreferences
                        ,getDialog(),
                        context,databaseReference,options
                        ,0
                        ,getString(R.string.input_key_uptocloud)));
        adapter.addFragment(getString(R.string.getdata_from_server)
                ,new CustomFragment().createInstance(sharedPreferences
                        ,getDialog()
                        ,context,databaseReference,options
                        ,1
                        ,getString(R.string.input_key_clone_data_from_server)));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.40);
        getDialog().getWindow().setLayout(width,height);
    }
}
