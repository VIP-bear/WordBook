package com.bear.wordbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private ImageButton search;     // 查询

    private ImageButton volume;     //语言

    private ImageButton help;       // 帮助

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        initLayout(view);
        setListener();
        return view;
    }

    private void initLayout(View view){
        search = view.findViewById(R.id.search_word);
        volume = view.findViewById(R.id.volume);
        help = view.findViewById(R.id.help);
    }

    private void setListener(){
        search.setOnClickListener(this);
        volume.setOnClickListener(this);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_word:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.volume:
                Intent volumeIntent = new Intent(getActivity(), SpeechActivity.class);
                startActivity(volumeIntent);
                break;
            case R.id.help:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Help")
                        .setMessage("this is help document...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
