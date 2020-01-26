package com.example.howtoday;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WeatherFragment extends Fragment {

    Button button;
    TextView textView;
    int cnt=0;


    public WeatherFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather,null);

        button = v.findViewById(R.id.btn);
        textView = v.findViewById(R.id.tv);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(""+cnt);
                cnt++;
            }
        });

        return v;
    }

}
