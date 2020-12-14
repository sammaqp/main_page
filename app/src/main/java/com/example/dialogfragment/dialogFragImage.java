package com.example.dialogfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

public class dialogFragImage extends DialogFragment {
    ImageView imageview,tc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialogfragimage,container,false);
        imageview = (ImageView)view.findViewById(R.id.big_image);
        tc = (ImageView) view.findViewById(R.id.bt2_close);
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    };

}
