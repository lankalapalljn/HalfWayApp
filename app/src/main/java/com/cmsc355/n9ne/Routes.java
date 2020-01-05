package com.cmsc355.n9ne;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Routes {
    private GoogleMap map;

    public Routes(GoogleMap map, LatLng locA, LatLng locB, LatLng mid){
        this.map = map;

        String url = getDirectionsUrl(locA, mid);
        String url2 = getDirectionsUrl(locB, mid);

        DownloadTask dTask = new DownloadTask();
        DownloadTask dTask2 = new DownloadTask();

        dTask.execute(url2);
        dTask2.execute(url);
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... url){

            String data = "";

            try{
                data = downloadUrl(url[0]);
            }catch (Exception e){
                Log.d("Exception DownloadTask:", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result){
            if(result.isEmpty()) return;
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {
        @Override
        protected List<List<HashMap<String,String>>> doInBackground(String... jsonData){
            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jsonObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser dParser = new DirectionsJSONParser();

                routes = dParser.parse(jsonObject);
            }catch(Exception e){
                Log.d("Exception Parser:", e.toString());
            }
            return routes;
        }
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result){
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            if(result.isEmpty()) return;
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.LTGRAY);
                lineOptions.geodesic(true);
            }

// Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest){
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
         return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyA2YY296MqbdFz4bKX0VCvCoLlltWgCKOI";
    }

    private String downloadUrl(String urlStr) throws IOException {
        String data = "";
        InputStream inStream = null;
        HttpURLConnection urlConnection = null;

        try{
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            StringBuffer sb = new StringBuffer();

            String line = "";

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();
        }catch (IOException e){
            Log.d("Exception ::", e.toString());
            data = "";
        }finally {
            try {
                inStream.close();
            }catch (NullPointerException e){
                System.exit(-1);
            }

            try {
                urlConnection.disconnect();
            }catch (NullPointerException e){
                System.exit(-1);
            }
        }

        return data;
    }
}
