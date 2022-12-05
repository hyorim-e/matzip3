package com.people.matzip3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Button btn_recent;

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

        btn_recent = (Button) findViewById(R.id.btn_recent);
        btn_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecentActivity.class);
                startActivity(intent); // 액티비티 이동
            }
        });

        Intent a = new Intent(getIntent());
        String pasteText = a.getStringExtra("복사");

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createClipData(arrayList.get(0).getTv_name() + "\n" + arrayList.get(0).getTv_addr());
                //arrayList.get(position).getTv_name()
                //createClipData(pasteText);
            }
        });
    }

    public void createClipData(String message){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        ClipData clipData = ClipData.newPlainText("Paste", message);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "복사되었습니다.", Toast.LENGTH_LONG).show();
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

                // 오늘 맛집 리스트
                Element today_list = doc.select("div.basic-post-gallery").first();
                //System.out.println("today_list = "+ today_list);

                Elements today_listItems = today_list.select("div.post-list"); // post-image(이미지), post-content(이름, 주소 등 내용)로 나뉨
                //System.out.println("today_listItems = "+ today_listItems);

                int today_shop_count = today_listItems.size(); // 오늘 가게 개수
                for (int i=0; i<today_shop_count; i++){
                    String imgUrl = today_listItems.select("img").eq(i).attr("src");

                    Elements shop_content = today_list.select("div.post-subject"); // 0123, 4567, 891011 +4단위 같은 name, menu, addr
                    String shop_name = shop_content.eq(i*4+1).text();
                    String shop_menu = shop_content.eq(i*4+2).text();
                    String shop_addr = shop_content.eq(i*4+3).text();

                    arrayList.add(new MainData(imgUrl, shop_name, shop_menu, shop_addr));
                    Log.d("items", "img: " + imgUrl + ", shop_name: " + shop_name + ", shop_menu: " + shop_menu + ", shop_addr: " + shop_addr);
                    //
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}