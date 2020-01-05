package com.cmsc355.n9ne;

import java.io.Serializable;

@FunctionalInterface
public interface PlacesURL extends Serializable {
    StringBuilder createURL(double latMid, Double lonMid);
}
