# SnapToRoad on Android Library
- This project refers [google API document](https://developers.google.com/maps/documentation/roads/snap?hl=ko).

### class MainActivity
- This class shows how you can implement the functions fully with android.os.Handler class as callback procedure.

### class SnapToRoad
- Thread extended Class for requesting to Google Road API Server and handling response with handler.

##### Code Snippet - Example for calling SnapToRoad
```java
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
```

- You can find the detail information for handling response from Google ROAD API on [google API document](https://developers.google.com/maps/documentation/roads/snap?hl=ko).

The original codes [on GitHub](https://github.com/yjham2002/GoogleSnapToRoad).

### Made By
- Ham EuiJin (Dongguk University - Dept. of Computer Science and Engineering)
