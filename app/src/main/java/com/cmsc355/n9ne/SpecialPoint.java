package com.cmsc355.n9ne;

import android.widget.CheckBox;

import java.io.Serializable;

public class SpecialPoint implements Serializable {
    private transient CheckBox checkBox; // needs to be transient to that it won't be serialized.
    private transient PlacesURL method;
    private String query;

    public void setMethod(PlacesURL method) {
        this.method = method;
    }
    public SpecialPoint(CheckBox checkBox){
        this.checkBox = checkBox;
    }
    public CheckBox getCheckBox(){
        return this.checkBox;
    }
    public void createUrl(double lat, double lon){
        this.query = this.method.createURL(lat, lon).toString();
    }
    public String getQuery(){
        return this.query;
    }
}
