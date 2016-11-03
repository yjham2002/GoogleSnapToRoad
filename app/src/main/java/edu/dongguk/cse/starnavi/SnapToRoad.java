package edu.dongguk.cse.starnavi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SnapToRoad extends Thread {

    private boolean isUnexecutable = false;

    private Handler mHandler;

    private static List<LatLng> resultData;

    private static final String API_KEY = "AIzaSyBgSmeVgqnGmRSIklhleML58X7LmaCrQkw";
    private static final String TAG = SnapToRoad.class.getSimpleName();

    private static final int CUTTING_POINT = 1;
    private static final int READ_TIME_OUT = 10000;
    private static final int CONNECT_TIME_OUT = 15000;

    private String tempURL;
    private String FOOTER = "&interpolate=true&key=" + API_KEY;

    public SnapToRoad(final List<LatLng> coordList, final Handler mHandler){
        if(coordList.size() < 2) isUnexecutable = true;
        resultData = new ArrayList<>();
        this.mHandler = mHandler;
        tempURL = "https://roads.googleapis.com/v1/snapToRoads?path=";
        for(LatLng latLng : coordList) tempURL += latLng.latitude + "," + latLng.longitude + "|";
        tempURL = tempURL.substring(0, tempURL.length() - CUTTING_POINT) + FOOTER;
        Log.e("Composite URL", tempURL);
    }

    private static String ReadHTML(String address, int timeout) {
        String html = new String();
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection != null) {
                urlConnection.setConnectTimeout(timeout);
                urlConnection.setUseCaches(false);
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    while (true) {
                        String buf = br.readLine();
                        if (buf == null)
                            break;
                        html += buf;
                    }
                    br.close();
                    urlConnection.disconnect();
                } else return null;
            } else return null;
        } catch (Exception ex) {return null;}
        return html;
    }

    @Override
    public void run(){
        if(isUnexecutable) return;
        String jsonString = ReadHTML(tempURL, 2000);
        if (jsonString == null) {
            jsonString = "";
        }
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("response", jsonString);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

}
