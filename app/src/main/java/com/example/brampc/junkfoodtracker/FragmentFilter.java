package com.example.brampc.junkfoodtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentFilter extends AppCompatDialogFragment {

    TextView radiusText;
    Button up;
    Button down;
    RadioButton mc;
    RadioButton sub;
    RadioButton all;
    Context context;
    int raduis;
    SettingsInfo info;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.zoeken_fragment,null);
        radiusText = view.findViewById(R.id.zoek_radius);
        up = view.findViewById(R.id.zoek_up);
        down = view.findViewById(R.id.zoek_down);
        mc = view.findViewById(R.id.radioButton_mac);
        sub = view.findViewById(R.id.radioButton_sub);
        all = view.findViewById(R.id.radioButton_all);
        context = view.getContext();
        final SettingsInfo info = new SettingsInfo(context);
        String keyword = info.loadKeyword();
        if(keyword.equals(""))
            all.setChecked(true);
        else if (keyword.equals("mac"))
            mc.setChecked(true);
        else if(keyword.equals("subway"))
            sub.setChecked(true);
        raduis = info.loadRadius();
        radiusText.setText((raduis / 1000)+"");
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(raduis < 30000){
                    raduis += 1000;
                    radiusText.setText((raduis / 1000)+"");
                }else {
                    Toast.makeText(context,"Maximum berijkt", Toast.LENGTH_SHORT).show();
                }
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(raduis > 1000){
                    raduis -= 1000;
                    radiusText.setText(raduis / 1000 + "");
                }else {
                    Toast.makeText(context,"Minimum berijkt", Toast.LENGTH_SHORT).show();
                }
            }
        });




        builder.setView(view)
                .setTitle("Filter")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(mc.isChecked())
                            info.saveKeyword("mac");
                        else
                            if (sub.isChecked())
                                info.saveKeyword("subway");
                        else
                            if (all.isChecked())
                                info.saveKeyword("");
                        info.saveRadius(raduis);
                    }
                });
        return builder.create();
    }




}
