package com.example.howtoday.Weather;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.howtoday.R;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WeatherFragment extends Fragment {

    public Float bodyTemp;
    int temp, mintemp, maxtemp, dVal,sdVal;
    String dustText, superdustText;
    String dustColor, superdustColor;
    String url = "https://search.naver.com/search.naver?sm=top_sug.pre&fbm=1&acr=1&acq=%EB%82%A0&qdt=0&ie=utf8&query=%EB%82%A0%EC%94%A8";
    String dUrl = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%EC%A0%84%EA%B5%AD%EB%AF%B8%EC%84%B8%EB%A8%BC%EC%A7%80";
    String sdUrl = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&query=%EC%A0%84%EA%B5%AD%EC%B4%88%EB%AF%B8%EC%84%B8%EB%A8%BC%EC%A7%80";
    Element tempElement, bodyTempElement, minTempElement, maxTempElement, dustValueElement, superdustValueElement;
    TextView temperature,bodyTemperature, minTemperature, maxTemperature, slash, textD, textSD, dustTextView, superdustTextView;

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
        textD = v.findViewById(R.id.textD);
        textSD = v.findViewById(R.id.textSD);
        dustTextView = v.findViewById(R.id.Dvalue);
        superdustTextView = v.findViewById(R.id.SDvalue);

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

                Connection.Response d_execute = Jsoup.connect(dUrl).execute();
                Document d_doc = Jsoup.parse(d_execute.body());

                Connection.Response sd_execute = Jsoup.connect(sdUrl).execute();
                Document sd_doc = Jsoup.parse(sd_execute.body());

                tempElement = document.getElementsByClass("todaytemp").get(0);
                bodyTempElement = document.getElementsByClass("num").get(2);
                minTempElement = document.getElementsByClass("num").get(0);
                maxTempElement = document.getElementsByClass("num").get(1);

                dustValueElement = d_doc.getElementsByClass("tb_scroll").get(0).getElementsByTag("table").get(0)
                        .getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(0)
                        .getElementsByTag("span").get(0);

                superdustValueElement = sd_doc.getElementsByClass("tb_scroll").get(0).getElementsByTag("table").get(0)
                        .getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(0)
                        .getElementsByTag("span").get(0);

                Log.e("asdf","doInBackground : "+tempElement+"and"+bodyTempElement);
                Log.e("qwer","doInbackground : "+minTempElement+"and"+maxTempElement);
                Log.e("zxcv","dust"+dustValueElement+"and"+superdustValueElement);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @NonNull
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

            if(dustValueElement != null){
                textD.setText("미세먼지");
                dVal = Integer.parseInt(dustValueElement.text());
                if(0<= dVal && dVal<=30){
                    dustColor = "#0b71fc";      //BLUE
                    dustText = "좋음";
                }
                else if(31<=dVal && dVal<=80){
                    dustColor = "#58cc00";        //GREEN
                    dustText = "보통";
                }
                else if(81<=dVal && dVal<=150){
                    dustColor = "#fb931a";       //ORANGE
                    dustText = "나쁨";
                }
                else{
                    dustColor = "#ff0000";          //RED
                    dustText = "매우 나쁨";
                }
                dustTextView.setTextColor(Color.parseColor(dustColor));
                dustTextView.setText(" "+dustText+"("+dVal+"㎍/㎥)");
            }

            if(superdustValueElement != null){
                textSD.setText("초미세먼지");
                sdVal = Integer.parseInt(superdustValueElement.text());
                if(0<= sdVal && sdVal<=15){
                    superdustColor = "#0b71fc";        //BLUE
                    superdustText = "좋음";
                }
                else if(16<=sdVal && sdVal<=35){
                    superdustColor = "#58cc00";       //GREEN
                    superdustText = "보통";
                }
                else if(36<=sdVal && sdVal<=75){
                    superdustColor = "#fb931a";      //ORANGE
                    superdustText = "나쁨";
                }
                else{
                    superdustColor = "#ff0000";      //RED
                    superdustText = "매우 나쁨";
                }
                superdustTextView.setTextColor(Color.parseColor(superdustColor));
                superdustTextView.setText(" "+superdustText+"("+sdVal+"㎍/㎥)");

            }

        }
    }

}
