package com.sp.rerofittry;

/**
 * Created by my on 2016/12/9.
 */

public class Weather {
    public AQI aqi;
}
class Basic {
    String city;
    String cnty;
    String id;
    String lat;
    String lon;
}
class CITY{
    String aqi;
    String co;
    String no2;
    String o3;
    String pm10;
    String pm25;
    String qlty;
    String so2;
}
class AQI{
    CITY city;
}