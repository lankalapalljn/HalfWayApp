package com.cmsc355.n9ne;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.Locale;
import android.graphics.Point;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latA;
    private double lonA;
    private double latB;
    private double lonB;
    private double latMid;
    private double lonMid;
    private String locAText;
    private String locBText;
    private String midPointText;
    private MapsActivity context;
    private double latRes;
    private double lonRes;
    private TextView locA;
    private TextView locB;
    private TextView midPoint;
    private TextView specialPointText;
    private boolean specialPointCheck = false;
    private SpecialPoint specialPoint;
    private Button specialShare;
    private String specialLocationString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Intent coordinates = getIntent();

        context = this;
        latA = coordinates.getDoubleExtra("latA", 0.0);
        lonA = coordinates.getDoubleExtra("lonA", 0.0);
        latB = coordinates.getDoubleExtra("latB", 0.0);
        lonB = coordinates.getDoubleExtra("lonB", 0.0);
        latMid = coordinates.getDoubleExtra("latMid", 0.0);
        lonMid = coordinates.getDoubleExtra("lonMid", 0.0);
        specialPointCheck = coordinates.getBooleanExtra("specialPointChecked", false);
        specialPoint = (SpecialPoint) coordinates.getSerializableExtra("specialPoint");
        specialShare = (findViewById(R.id.shareSpecial));

        locA = findViewById(R.id.locA);
        locB = findViewById(R.id.locB);
        midPoint = findViewById(R.id.midPoint);
        specialPointText = findViewById(R.id.specialLocation);

        updateUIAdressStrings();

        renderMap();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        try{
            mapFragment.getMapAsync(this);
        }catch (NullPointerException e){
            System.exit(-1);
        }


        // making a new share button functional for sharing the special location via text

        specialShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specialLocationString = specialPointText.getText().toString();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, specialLocationString);
                Intent sharing = Intent.createChooser(shareIntent, null);
                startActivity(sharing);}
            });
        }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // put each coordinate in an array
        LatLng[] cordsAB = {new LatLng(latA, lonA), new LatLng(latB, lonB), new LatLng(latMid, lonMid)};

        showLocations(cordsAB);
    }

    /**
     * Method to conditionally render the map size depending on if the special location
     * was selected.
     */
    public void renderMap(){
        Fragment map = (getSupportFragmentManager().findFragmentById(R.id.map));
        ViewGroup.LayoutParams mapParams = map.getView().getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Button settings = findViewById(R.id.goToSettings);
        settings.measure(0,0);
        specialPointText.measure(0,0);
        midPoint.measure(0, 0);
        locA.measure(0, 0);
        locB.measure(0,0);

        if(specialPointCheck) {
            mapParams.height = size.y - (specialPointText.getMeasuredHeight() + midPoint.getMeasuredHeight() + locA.getMeasuredHeight() + locB.getMeasuredHeight() + settings.getMeasuredHeight());
            specialPointText.setWidth(20);
            findSpecialPoint();
            specialPointText.setVisibility(View.VISIBLE);
            specialShare.setVisibility(View.VISIBLE);
        }else{
            mapParams.height = size.y - (midPoint.getMeasuredHeight() + locA.getMeasuredHeight() + locB.getMeasuredHeight() + settings.getMeasuredHeight());
            specialShare.getLayoutParams().height = 0;
            specialPointText.getLayoutParams().height = 0;
        }
    }

    /**
     * Method to show the default locations A, B, and literal midpoint between the two.
     * Easier to put in separate for reading purposes.
     */
    public void showLocations(LatLng[] coordinates ){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (LatLng point : coordinates) {
            builder.include(point);
            if (point == coordinates[0]) {
                mMap.addMarker(new MarkerOptions().position(point).title(locAText).snippet("Location A")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (point == coordinates[1]) {
                mMap.addMarker(new MarkerOptions().position(point).title(locBText).snippet("Location B")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else {
                mMap.addMarker(new MarkerOptions().position(point).title(midPointText).snippet("Midpoint"));
            }
        }

        LatLngBounds points = builder.build();

        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(points, 100);

        Routes routes = new Routes(mMap, coordinates[0], coordinates[1], coordinates[2]);
        mMap.animateCamera(camera);

    }

    /**
     * Method for the share button onClick listener. Moved to clean up code.
     * @param view required to use views
     */
    public void clickToShare(View view){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, midPointText);
        Intent share = Intent.createChooser(shareIntent, null);
        startActivity(share);
    }

    /**
     * Show the correct addresses on the textViews
     */
    public void updateUIAdressStrings() {
        locAText = "";
        locBText = "";
        midPointText = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latA, lonA, 1);
            locAText = addresses.get(0).getAddressLine(0);

            locA.setText("A: " + locAText);
            //locA.setBackgroundColor(Color.rgb(1,100,7));
           // locA.setTextColor(Color.rgb(1,100,7));
            locA.setTextColor(Color.BLACK);

            addresses = geocoder.getFromLocation(latB, lonB, 1);
            locBText = addresses.get(0).getAddressLine(0);

            locB.setText("B: " + locBText);
          //locB.setTextColor(Color.rgb(0,0,150);
            //locB.setBackgroundColor(Color.rgb(0,0,150));
            locB.setTextColor(Color.BLACK);
            addresses = geocoder.getFromLocation(latMid, lonMid, 1);
            midPointText = addresses.get(0).getAddressLine(0);

            midPoint.setText("Mid: " + midPointText);
          //  midPoint.setTextColor(Color.rgb(150,0,0));
            //midPoint.setBackgroundColor(Color.rgb(150,0,0));
            midPoint.setTextColor(Color.BLACK);

            locA.setTypeface(null, Typeface.BOLD);
           locB.setTypeface(null, Typeface.BOLD);
            midPoint.setTypeface(null, Typeface.BOLD);

        } catch (IOException e) {
            Toast.makeText(this, "DEBUG: Unable to get Addresses", Toast.LENGTH_LONG).show();
        }
    }

    // Method to go back to settings. Resolves map activity before returning.
    public void returnToSettings(View v) {
        mMap.clear();
        finish();
    }

    public void findSpecialPoint() {
        PlacesTask placesTask = new PlacesTask();
        placesTask.execute(specialPoint.getQuery());
    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {
        String resultString = null;

        // Invoked by execute() method called in findMidPointRestaurant()
        @Override
        protected String doInBackground(String[] url) {
            try {
                // Download url creates internet connection and returns string with all json data
                resultString = placesConnect(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            //System.out.println(data);
            // This data
            return resultString;
        }

        // Executes after doInBackground() finishes
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parserTask.execute(result);
        }
    }

    private String placesConnect(String requestUrl) throws IOException {
        String resultString = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(requestUrl);

            // Create http connection to communicate with url and connect
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            // Read data from url
            inputStream = urlConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            // Seems to be useless code
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            resultString = stringBuffer.toString();
            bufferedReader.close();

        } catch (Exception e) {
            Log.d("Error while downloading", e.toString());
        } finally {
            try {
                inputStream.close();
                urlConnection.disconnect();
            }catch (Exception e){
                System.exit(-1);
            }
        }
        //System.out.println(resultString);
        return resultString;
    }

    // AsyncTaks helps to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jsonDataObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String[] jsonStr) {
            List<HashMap<String, String>> placesList = null;
            PlaceQueryResultJSON placeQueryResultJSON = new PlaceQueryResultJSON();

            try {
                jsonDataObject = new JSONObject(jsonStr[0]);
                placesList = placeQueryResultJSON.parseJSONObject(jsonDataObject);
            } catch (Exception e) {
                Log.d("Error while parsing", e.toString());
            }

            return placesList;
        }


        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            if(list.size() == 0){
                Toast.makeText(context, "No midpoint special location found!", Toast.LENGTH_LONG).show();
                return;
            }
            Log.d("Map", "list size: " + list.size());

            // Create a map a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Grab just top entry on list; it will return the closest location
            HashMap<String, String> hashMapPlace = list.get(0);

            // Get various data points for the place
            latRes = Double.parseDouble(hashMapPlace.get("lat"));
            lonRes = Double.parseDouble(hashMapPlace.get("lng"));

            String name = hashMapPlace.get("place_name");
            String vicinity = hashMapPlace.get("vicinity");

            // Puts out info to logcat
            Log.d("Map", "place: " + name + " Vicinity: " + vicinity);

            LatLng latLng = new LatLng(latRes, lonRes);

            // Setting the position for the marker
            markerOptions.position(latLng);
            specialPointText.setText(name + " " + vicinity);
            markerOptions.title(name + " : " + vicinity);
           // specialPointText.setTextColor(Color.rgb(255,25,255));
            //specialPointText.setBackgroundColor(Color.rgb(255,25,255));
           specialPointText.setTypeface(null, Typeface.BOLD);
            specialPointText.setTextColor(Color.BLACK);
            // Color of other waypoints
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            // Placing a marker on the touched position
            Marker marker = mMap.addMarker(markerOptions);

            }
        }
    }

    // Inner class helps with functionality, only used here
    class PlaceQueryResultJSON {
        // Converts JSONObject to a list
        public List<HashMap<String, String>> parseJSONObject(JSONObject jsonObject) {
            JSONArray jsonResults = null;

            try {
                // Retrieves all the elements in the 'places' array
                jsonResults = jsonObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Invoking getPlaces with the array of json object where each json object represent a place
            return parseJSONArray(jsonResults);
        }

        private List<HashMap<String, String>> parseJSONArray(JSONArray jsonArray) {
            //int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            // Taking each place, parses and adds to list object
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    // Call getPlace with place JSON object to parse the place
                    place = parseEachPlace((JSONObject) jsonArray.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        /**
         * Parsing the Place JSON object
         */
        private HashMap<String, String> parseEachPlace(JSONObject jPlace) {
            HashMap<String, String> placeInfo = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";

            try {
                // Place name
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Place viciinity
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");

                // Store corresponding info in map entries
                placeInfo.put("place_name", placeName);
                placeInfo.put("vicinity", vicinity);
                placeInfo.put("lat", latitude);
                placeInfo.put("lng", longitude);
                placeInfo.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return placeInfo;
        }
    }


