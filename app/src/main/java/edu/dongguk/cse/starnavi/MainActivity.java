package edu.dongguk.cse.starnavi;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PolylineOptions partPath;
    private List<LatLng> responseList;

    private static final LatLng TEST_POSITION = new LatLng(-35.27801,149.12958);
    private GoogleMap map;

    public void initMap(){
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(TEST_POSITION, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMap();

        /**
         * Example Code Snippet By EuiJin.Ham.
         * Anyone can contact to me via github[https://github.com/yjham2002]
         * */

        responseList = new ArrayList<>();

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
                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("snappedPoints");
                for(JsonElement jsonObj : jsonArray){
                    /**
                     * CODE WHAT YOU WANNA DO WITH RESPONSE HERE
                     * */
                    JsonObject locationData = jsonObj.getAsJsonObject();
                    double latitude = locationData.get("location").getAsJsonObject().get("latitude").getAsDouble();
                    double longitude = locationData.get("location").getAsJsonObject().get("longitude").getAsDouble();
                    responseList.add(new LatLng(latitude, longitude));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        partPath = new PolylineOptions().color(getResources().getColor(R.color.colorAccent)).width(15.0f);
                        partPath.addAll(responseList);
                        map.addPolyline(partPath);
                    }
                });
            }
        }).start();

    }
}
