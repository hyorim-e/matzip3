package com.people.matzip3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private TextView textView_title, textView_release, textView_director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_title = (TextView) findViewById(R.id.textView_title);
        textView_release = (TextView) findViewById(R.id.textView_release);
        textView_director = (TextView) findViewById(R.id.textView_director);

        WeatherConnection weatherConnection = new WeatherConnection();
        
        AsyncTask<String, String, String> result = weatherConnection.execute("","");

        System.out.println("RESULT");

        try {

//            String a = "aaa||bbb||ccc||ddd";
//            String[] results = a.split("\\|\\|"); // ||문자를 기준으로 자른다.
//            for (int i = 0; i < results.length; i++) {
//                Log.e("results", i + " " + results[i]);
//            }
            String msg = result.get();
            System.out.println("msg ="+ msg);

            String[] results = msg.split(",");
            String name = results[0];
            String menu = results[1];
            String addr = results[2];

            textView_title.setText(name);
            textView_release.setText(menu);
            textView_director.setText(addr);

        }catch (Exception e){

        }
    }

    public class WeatherConnection extends AsyncTask<String, String, String>{

        // 백그라운드에서 작업하게 한다
        @Override
        protected String doInBackground(String... params) {
            try{

                String path = "https://www.menutong.com/";
                Document doc = Jsoup.connect(path).get();

                Element all_list = doc.select("div.basic-post-gallery").first();
                System.out.println("all_list = "+ all_list);

                Elements today_list = all_list.select("div.post-content");
                System.out.println("today_list = "+ today_list);

                Elements today_shop1 = today_list.get(0).select(".post-subject");
                // .first() == .get(0)
                System.out.println("today_shop1 = "+ today_shop1);

                String shop1_name = today_shop1.get(1).text();
                String shop1_menu = today_shop1.get(2).text();
                String shop1_addr = today_shop1.get(3).text();

                String shop1_info = shop1_name+","+shop1_menu+","+shop1_addr;
                System.out.println("shop1_info = "+ shop1_info);
                //for(Element elem : today_shop1){
//                    String name = elem.select(".post-subject").get(1).text();
//                }
//                System.out.println("elements ="+elements);
//
//                Element targetElement = elements.get(1);

                //String text = targetElement.text();
                //String text = elements.text();

                //System.out.println("text ="+text);

                return shop1_info;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}