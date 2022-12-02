package com.people.matzip3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private TextView textView_title, textView_release, textView_director;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true); // 외국유튜브 코드 추가
        recyclerView.setLayoutManager(linearLayoutManager);


        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        // AsyncTask 작동시킴(파싱)
        new Content().execute();

        /*
        // 버튼 클릭 시
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData mainData = new MainData(R.mipmap.ic_launcher, "가게이름", "메뉴", "주소");
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged(); // 새로고침
            }
        });

         */

//        textView_title = (TextView) findViewById(R.id.textView_title);
//        textView_release = (TextView) findViewById(R.id.textView_release);
//        textView_director = (TextView) findViewById(R.id.textView_director);
//        list = (ListView) findViewById(R.id.list);

//        List<String> data = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
//        list.setAdapter(adapter);
//        data.add("홍드로이드");

/*
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

 */
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 프로그레스바 내용 생략
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            // 프로그레스바 내용 생략
            mainAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.menutong.com/";
                Document doc = Jsoup.connect(url).get();

                Element today_list = doc.select("div.basic-post-gallery").first();
                System.out.println("today_list = "+ today_list);

                Elements today_list_content = today_list.select("div.post-content");
                System.out.println("today_list_content = "+ today_list_content);
                int today_shop_count = today_list_content.size(); // 오늘 가게 개수
                for (int i=0; i<today_shop_count*4;){
                    Elements shop_info = today_list.select("div.post-subject"); // 0123, 4567, 891011 +4단위 같은 name, menu, addr
                    //String imgUrl = shop_info.eq(i).text();
                    int imgUrl = 1;
                    String shop_name = shop_info.eq(i+1).text();
                    String shop_menu = shop_info.eq(i+2).text();
                    String shop_addr = shop_info.eq(i+3).text();
                    i = i+4;
                    arrayList.add(new MainData(imgUrl, shop_name, shop_menu, shop_addr));
                    Log.d("items", "img: " + imgUrl + ", shop_name: " + shop_name + ", shop_menu: " + shop_menu + ", shop_addr: " + shop_addr);
                }

//                Elements today_shop1 = today_list.get(0).select(".post-subject");
//                // .first() == .get(0)
//                System.out.println("today_shop1 = "+ today_shop1);
//
//                String shop1_name = today_shop1.get(1).text();
//                String shop1_menu = today_shop1.get(2).text();
//                String shop1_addr = today_shop1.get(3).text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}