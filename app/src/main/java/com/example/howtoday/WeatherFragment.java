package com.example.howtoday;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WeatherFragment extends Fragment {

    Float bodyTemp;
    int temp, mintemp, maxtemp;
    String url = "https://search.naver.com/search.naver?sm=top_sug.pre&fbm=1&acr=1&acq=%EB%82%A0&qdt=0&ie=utf8&query=%EB%82%A0%EC%94%A8";
    Element tempElement, bodyTempElement, minTempElement, maxTempElement;
    TextView temperature,bodyTemperature, minTemperature, maxTemperature, slash;

    public WeatherFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_weather,null);
        temperature = v.findViewById(R.id.temperature);
        bodyTemperature = v.findViewById(R.id.bodyTemperature);
        minTemperature = v.findViewById(R.id.minTemperature);
        maxTemperature = v.findViewById(R.id.maxTemperature);
        slash = v.findViewById(R.id.slash);


        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

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

                tempElement = document.getElementsByClass("todaytemp").get(0);
                bodyTempElement = document.getElementsByClass("num").get(2);
                minTempElement = document.getElementsByClass("num").get(0);
                maxTempElement = document.getElementsByClass("num").get(1);

                Log.e("asdf","doInBackground : "+tempElement+"and"+bodyTempElement);
                Log.e("qwer","doInbackground : "+minTempElement+"and"+maxTempElement);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(tempElement != null){
                temp = Integer.parseInt(tempElement.text());
                temperature.setText(temp+"℃");
            }
            if(bodyTempElement != null){
                bodyTemp = Float.parseFloat(bodyTempElement.text());
                bodyTemperature.setText("체감온도 "+bodyTemp+"℃");
            }

            if(minTempElement != null){
                mintemp = Integer.parseInt(minTempElement.text());
                minTemperature.setText(mintemp+"℃");
                slash.setText(" / ");
            }

            if(maxTempElement != null){
                maxtemp = Integer.parseInt(maxTempElement.text());
                maxTemperature.setText(maxtemp+"℃");
            }

        }
    }

}
