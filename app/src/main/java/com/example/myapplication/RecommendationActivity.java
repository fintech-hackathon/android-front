package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.util.NetworkTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        recyclerView = findViewById(R.id.musicRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        String result;
        String url = "http://115.85.180.70:3001/record/getList";
        JSONObject object = new JSONObject();

        // TODO : DB에서 노래정보 불러오면 됩니다.
        int[] image = {R.mipmap.ic_musicimage_round, R.mipmap.ic_musicimage_round};
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> singer = new ArrayList<>();
        ArrayList<String> youtube_url = new ArrayList<>();
        ArrayList<Integer> view = new ArrayList<>();
        ArrayList<Integer> likes = new ArrayList<>();
        try {
            NetworkTask parser = new NetworkTask(url, object, "POST");
            result = parser.execute().get();
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = (JSONObject) array.get(i);
                title.add(temp.get("r_title").toString());
                singer.add(temp.get("r_name").toString());
                youtube_url.add(temp.get("r_url").toString());
                view.add(Integer.parseInt(temp.get("r_views").toString()));
                likes.add(Integer.parseInt(temp.get("r_likes").toString()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new MusicAdapter(image, title, singer, youtube_url, view, likes);

        recyclerView.setAdapter(adapter);
    }
}