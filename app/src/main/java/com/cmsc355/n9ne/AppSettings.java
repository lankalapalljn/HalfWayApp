package com.cmsc355.n9ne;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.provider.Settings;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppSettings extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private Geocoder geocode;
    private EditText locationA;
    private EditText locationB;
    private double latA;
    private double lonA;
    private double latB;
    private double lonB;
    private double latMid;
    private double lonMid;
    private boolean specialPointChecked = false;
    private SpecialPoint restaurantCheckBox;
    private SpecialPoint parkCheckBox;
    private SpecialPoint gasStationCheckBox;
    private SpecialPoint hotelCheckBox;
    private List<SpecialPoint> allCheckBoxes;
    private SpecialPoint theSpecialPoint;
    private TextView percentageLabel;
    private SeekBar percentage;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        geocode = new Geocoder(this);
        locationA = findViewById(R.id.locationA);
        locationA.setTypeface(null, Typeface.BOLD);
        locationB = findViewById(R.id.locationB);
        locationB.setTypeface(null, Typeface.BOLD);
        allCheckBoxes = new ArrayList<>();
        theSpecialPoint = null;
        percentageLabel = findViewById(R.id.sliderText);
        percentage = findViewById(R.id.slider);
        percentage.setProgress(50);
        progress = percentage.getProgress();
        percentageLabel.setText(R.string.labelHalfway);
        percentage.setOnSeekBarChangeListener(sbChangeListener);

        setupSpecialPoints();
    }

    SeekBar.OnSeekBarChangeListener sbChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if(i == 50) percentageLabel.setText(R.string.labelHalfway);
            else if(i >= 0 && i <= 49) percentageLabel.setText((50-i)*2 + "% closer to A");
            else if(i >= 51 && i <= 100) percentageLabel.setText((i - 50)*2 + "% closer to B");
            else percentageLabel.setText("" + R.string.midpointColon + i + R.string.labelPercent);
            progress = i;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void setupSpecialPoints(){

        restaurantCheckBox = new SpecialPoint((CheckBox) findViewById(R.id.checkBoxRestaurant));
        restaurantCheckBox.getCheckBox().setOnClickListener(this);
        restaurantCheckBox.setMethod(new PlacesURL() {
            @Override
            public StringBuilder createURL(double latMid, Double lonMid) {
                StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                query.append("location=" + latMid + "," + lonMid);
                query.append("&rankby=distance");
                query.append("&type=" + "restaurant");
                query.append("&keyword=food");
                query.append("&key=AIzaSyAhiO-Pyk7skk5Q-WDw38-WXsP0rbDkaCs");
                return query;
            }
        });

        parkCheckBox = new SpecialPoint((CheckBox) findViewById(R.id.checkBoxPark));
        parkCheckBox.getCheckBox().setOnClickListener(this);
        parkCheckBox.setMethod(new PlacesURL() {
            @Override
            public StringBuilder createURL(double latMid, Double lonMid) {
                StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                query.append("location=" + latMid + "," + lonMid);
                query.append("&rankby=distance");
                query.append("&type=" + "park");
                query.append("&key=AIzaSyAhiO-Pyk7skk5Q-WDw38-WXsP0rbDkaCs");
                return query;
            }
        });

        gasStationCheckBox = new SpecialPoint((CheckBox) findViewById(R.id.checkBoxGasStation));
        gasStationCheckBox.getCheckBox().setOnClickListener(this);
        gasStationCheckBox.setMethod(new PlacesURL() {
            @Override
            public StringBuilder createURL(double latMid, Double lonMid) {
                StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                query.append("location=" + latMid + "," + lonMid);
                query.append("&rankby=distance");
                query.append("&type=" + "gas_station");
                query.append("&key=AIzaSyAhiO-Pyk7skk5Q-WDw38-WXsP0rbDkaCs");
                return query;
            }
        });

        hotelCheckBox = new SpecialPoint((CheckBox) findViewById(R.id.checkBoxHotel));
        hotelCheckBox.getCheckBox().setOnClickListener(this);
        hotelCheckBox.setMethod(new PlacesURL() {
            @Override
            public StringBuilder createURL(double latMid, Double lonMid) {
                StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                query.append("location=" + latMid + "," + lonMid);
                query.append("&rankby=distance");
                query.append("&type=" + "lodging");
                query.append("&key=AIzaSyAhiO-Pyk7skk5Q-WDw38-WXsP0rbDkaCs");
                return query;
            }
        });

        allCheckBoxes.add(gasStationCheckBox);
        allCheckBoxes.add(parkCheckBox);
        allCheckBoxes.add(restaurantCheckBox);
        allCheckBoxes.add(hotelCheckBox);
    }

    /**
     * Only allow one checkbox to be checked at any given time.
     */
    @Override
    public void onClick(View view){
        int actualClicked = view.getId();

        for(SpecialPoint box: allCheckBoxes){
            int tempId = box.getCheckBox().getId();
            CheckBox currentBox = box.getCheckBox();

            if(tempId == actualClicked && currentBox.isChecked()){
                currentBox.setChecked(true);
            }else{
                currentBox.setChecked(false);
            }
        }
    }

    /**
     * Checks if any of the special points are checked.
     */
    public boolean isSpecialPointChecked(){
        for(SpecialPoint box: allCheckBoxes){
            CheckBox current = box.getCheckBox();
            if(current.isChecked()){
                theSpecialPoint = box;
                box.createUrl(latMid, lonMid);
                return true;
            }
        }
        return false;
    }

    public void gotoMap(View v){
        Intent mapInfo = new Intent(this, MapsActivity.class);

        // set both coordinates based off of the input in each box
        if(!setBothCoordinates()) return;

        specialPointChecked = isSpecialPointChecked();

        mapInfo.putExtra("latA", latA);
        mapInfo.putExtra("lonA", lonA);
        mapInfo.putExtra("latB", latB);
        mapInfo.putExtra("lonB", lonB);
        mapInfo.putExtra("latMid", latMid);
        mapInfo.putExtra("lonMid", lonMid);
        mapInfo.putExtra("specialPointChecked", specialPointChecked);
        mapInfo.putExtra("specialPoint", theSpecialPoint);
        // need to include a name for the special point.
        startActivity(mapInfo);
    }

    public void getLocation(View v){
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // now we need to check if the GPS is enabled.
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            turnOnGPS();
        }else{
            // we get the location
            getCurrentLocation();
        }
    }


    private double[] getCoordinates(EditText location){

        double[] cords = new double[2];

        String address = location.getText().toString();
        List<Address> coordinates;

        try{
            coordinates = geocode.getFromLocationName(address, 1);
            if(coordinates.size() == 0){
                return null;
            }
        }catch (IOException e){
            return null;
        }

        cords[0] = coordinates.get(0).getLatitude();
        cords[1] = coordinates.get(0).getLongitude();

        return cords;
    }

    /**
     * Method takes whatever is currently in each location text box and validates that each location
     * returned something meaningful
     */
    public boolean setBothCoordinates(){

        double[] results;

        if((results = getCoordinates(locationA)) == null){
            Toast.makeText(this, "Try another location for A!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            latA = results[0];
            lonA = results[1];
        }

        if((results = getCoordinates(locationB)) == null){
            Toast.makeText(this, "Try another location for B!", Toast.LENGTH_LONG).show();
            return false;
        }else{
            latB = results[0];
            lonB = results[1];
        }

        getMidPoint();

        return true;
    }

    private void getMidPoint(){
          double multiplier = ((double) progress) / 100.0;
          latMid = latA + (multiplier)*(latB - latA);
          lonMid = lonA + (multiplier)*(lonB - lonA);
    }

    private void getCurrentLocation(){
        // check permissions
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            // request permissions again.
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location locGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            double lat, lon;

            if(locGPS != null){
                lat = locGPS.getLatitude();
                lon = locGPS.getLongitude();
            }else if(locationNetwork != null){
                lat = locationNetwork.getLatitude();
                lon = locationNetwork.getLongitude();
            }else if(locationPassive != null){
                lat = locationPassive.getLatitude();
                lon = locationPassive.getLongitude();
            }else{
                return;
            }

            List<Address> here;

            // actually request current location
            try{
                here = geocode.getFromLocation(lat, lon, 1);
            }catch(IOException e){
                Toast.makeText(this, "In the IO Exception", Toast.LENGTH_SHORT).show();
                return;
            }

            locationA.setText(here.get(0).getAddressLine(0));
        }
    }

    // method to turn the GPS settings to ON
    private void turnOnGPS(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        // create the dialog to show to the user
        final AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}