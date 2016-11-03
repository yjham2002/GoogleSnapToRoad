package edu.dongguk.cse.starnavi;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Example Code Snippet By EuiJin.Ham.
         * Anyone can contact to me via github[https://github.com/yjham2002]
         * */

        List<LatLng> sampleData = new ArrayList<>();

        sampleData.add(new LatLng(-35.27801,149.12958));
        sampleData.add(new LatLng(-35.28032,149.12907));
        sampleData.add(new LatLng(-35.28099,149.12929));
        sampleData.add(new LatLng(-35.28144,149.12984));
        sampleData.add(new LatLng(-35.28194,149.13003));
        sampleData.add(new LatLng(-35.28282,149.12956));

        new SnapToRoad(sampleData, new Handler(){
            @Override
            public void handleMessage(Message message){
                String response = message.getData().getString("response");
                Log.e("JSON DATA", response);
            }
        }).start();

    }
}
