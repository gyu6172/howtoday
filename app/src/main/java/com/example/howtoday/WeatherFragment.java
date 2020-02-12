package com.example.howtoday;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.howtoday.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.ConnectException;

public class WeatherFragment extends Fragment {

    TextView textView;
    Button button;
    Float temp;
    int cnt=1;
    String url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%ED%98%84%EC%9E%AC%EB%82%A0%EC%94%A8";
    Element element;

    public WeatherFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather,null);

        textView = v.findViewById(R.id.textView);
        button = v.findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();

                textView.setText(""+temp);
            }
        });

        return v;
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Connection.Response execute = Jsoup.connect(url).execute();
                Document document = Jsoup.parse(execute.body());

                element = document.getElementsByClass("todaytemp").get(0);
                Log.e("as","doInBackground : " + element.text());


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (element != null){
                temp = Float.parseFloat(element.text());
            }

        }
    }

}
