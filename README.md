# SnapToRoad on Android Library
- This project refers [google API document](https://developers.google.com/maps/documentation/roads/snap?hl=ko).

### class MainActivity
- This class shows how you can implement the functions fully with android.os.Handler class as callback procedure.

### class SnapToRoad
- Thread extended Class for requesting to Google Road API Server and handling response with handler.

##### Code Snippet - Example for calling SnapToRoad
```java
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
                // DO WHAT YOU WANNA DO HERE
            }
        }).start();
```

- You can find the detail information for handling response from Google ROAD API on [google API document](https://developers.google.com/maps/documentation/roads/snap?hl=ko).

The original codes [on GitHub](https://github.com/yjham2002/GoogleSnapToRoad).

### Made By
- Ham EuiJin (Dongguk University - Dept. of Computer Science and Engineering)
