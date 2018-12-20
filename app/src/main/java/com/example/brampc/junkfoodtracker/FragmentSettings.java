package com.example.brampc.junkfoodtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FragmentSettings extends AppCompatDialogFragment {

    Context context;
    RadioButton nl;
    RadioButton en;
    RadioButton fr;
    TextView naam;
    Button signOut;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.settings_fragment,null);
        context = view.getContext();
        SettingsInfo info = new SettingsInfo(context);
        nl = view.findViewById(R.id.set_nl);
        en = view.findViewById(R.id.set_en);
        fr = view.findViewById(R.id.set_fr);
        naam = view.findViewById(R.id.set_naam);
        signOut = view.findViewById(R.id.set_SignOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        naam.setText(info.loadNaam());
        String lang = info.loadLanguage();
        if(lang.equals("nl"))
            nl.setChecked(true);
        else if (lang.equals("en"))
            en.setChecked(true);
        else if(lang.equals("fr"))
            fr.setChecked(true);

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
                        SettingsInfo info = new SettingsInfo(context);
                        if(nl.isChecked())
                            info.saveLanguage("nl");
                        else
                        if (en.isChecked())
                            info.saveLanguage("en");
                        else
                        if (fr.isChecked())
                            info.saveLanguage("fr");
                    }
                });


        return builder.create();
    }

    private void signOut(){
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
