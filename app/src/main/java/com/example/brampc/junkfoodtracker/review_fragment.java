package com.example.brampc.junkfoodtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class review_fragment extends AppCompatDialogFragment {


    Context context;
    SeekBar bar;
    TextView rating;
    TextView description;
    String placeId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_review_fragment,null);
        bar = view.findViewById(R.id.ratingBar);
        rating = view.findViewById(R.id.maximum_stars);
        bar.setMax(5);
        description = view.findViewById(R.id.review_reviewText_Textview);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rating.setText(seekBar.getProgress()+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        context = view.getContext();
        final SettingsInfo info = new SettingsInfo(context);

        builder.setView(view)
                .setTitle("Review")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Plaats", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBase dataBase = new DataBase();
                        java.util.Date date = new java.util.Date();
                        Review review = new Review(bar.getProgress(),info.loadNaam(),description.getText().toString(),placeId, date.toString());
                        dataBase.addData(review);
                        Toast.makeText(context, "Review is geplaatst", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    public void show(FragmentManager manager, String tag, String placeId) {
        super.show(manager, tag);
        this.placeId =placeId;
    }
}
