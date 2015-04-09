package com.cmu.cpe.se.blarblarblar.lookaround;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class GoogleStaticMaps {
    public final static String FORMAT_PNG8 = "png8";
    public final static String FORMAT_PNG32 = "png32";
    public final static String FORMAT_JPEG = "jpg";
    public final static String FORMAT_JPEG_NONPROG = "jpg-baseline";
    public final static String FORMAT_GIF = "gif";


    public final static String TYPE_DEFAULT = "roadmap";
    public final static String TYPE_SATELLITE = "satellite";
    public final static String TYPE_TERRAIN = "terrain";
    public final static String TYPE_HYBRID = "hybrid";

    public final static int MAX_SIZE = 1280;

    private String mapType = TYPE_DEFAULT;
    private String format = FORMAT_PNG8;
    private String language = Locale.getDefault().getLanguage();

    private int width = 200;
    private int height = 200;
    private int scale = 1;

    private ArrayList<String> arr_color = new ArrayList<String>();
    private ArrayList<String> arr_label = new ArrayList<String>();
    private ArrayList<Double> arr_lat = new ArrayList<Double>();
    private ArrayList<Double> arr_lon = new ArrayList<Double>();

    public GoogleStaticMaps() { }

    public GoogleStaticMaps(String mapType) {
        this.mapType = mapType;
    }

    public GoogleStaticMaps(String mapType, String language) {
        this.mapType = mapType;
        this.language = language;
    }

    public GoogleStaticMaps(int width, int height) {
        if(width <= 640 || height <= 640) {
            scale = 1;
            this.width = width;
            this.height = height;
        } else {
            scale = 2;
            this.width = width / 2;
            this.height = height / 2;
        }
    }

    public GoogleStaticMaps(int width, int height, String mapType) {
        if(width <= 640 || height <= 640) {
            scale = 1;
            this.width = width;
            this.height = height;
        } else {
            scale = 2;
            this.width = width / 2;
            this.height = height / 2;
        }
        this.mapType = mapType;
    }

    public GoogleStaticMaps(int width, int height, String mapType
            , String language) {
        if(width <= 640 || height <= 640) {
            scale = 1;
            this.width = width;
            this.height = height;
        } else {
            scale = 2;
            this.width = width / 2;
            this.height = height / 2;
        }
        this.mapType = mapType;
        this.language = language;
    }

    public void setWidth(int width) {
        if(width <= 640) {
            scale = 1;
            this.width = width;
        } else {
            scale = 2;
            if(width >= 1280)
                width = 1280;
            this.width = width / 2;
            this.height = this.height / 2;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        if(height <= 640) {
            scale = 1;
            this.height = height;
        } else {
            scale = 2;
            if(height >= 1280)
                height = 1280;
            this.width = this.width / 2;
            this.height = height / 2;
        }
    }

    public int getHeight() {
        return this.height;
    }

    public void setSize(int width, int height) {
        if(width <= 640 || height <= 640) {
            scale = 1;
            this.width = width;
            this.height = height;
        } else {
            scale = 2;
            this.width = width / 2;
            this.height = height / 2;
        }
    }

    public int[] getSize() {
        return new int[] { this.width, this.height };
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setImageFormat(String format) {
        if(format.equals(FORMAT_PNG8)
                || format.equals(FORMAT_PNG32)
                || format.equals(FORMAT_GIF)
                || format.equals(FORMAT_JPEG)
                || format.equals(FORMAT_JPEG_NONPROG))
            this.format = format;
        else
            this.format = null;
    }

    public void setMapType(String mapType) {
        if(mapType.equals(TYPE_DEFAULT)
                || mapType.equals(TYPE_SATELLITE)
                || mapType.equals(TYPE_TERRAIN)
                || mapType.equals(TYPE_HYBRID))
            this.mapType = mapType;
        else
            this.mapType = null;
    }

    public String getMapType() {
        return this.mapType;
    }

    public void addMarker(double latitude, double longitude
            , String color, String label) {
        this.arr_color.add(color);
        this.arr_label.add(label);
        this.arr_lat.add(latitude);
        this.arr_lon.add(longitude);
    }

    public void addMarker(double latitude, double longitude
            , String label) {
        this.arr_color.add(null);
        this.arr_label.add(label);
        this.arr_lat.add(latitude);
        this.arr_lon.add(longitude);
    }

    public void addMarker(double latitude, double longitude) {
        this.arr_color.add(null);
        this.arr_label.add("");
        this.arr_lat.add(latitude);
        this.arr_lon.add(longitude);
    }

    public void removeMarker(int position) {
        this.arr_color.remove(position);
        this.arr_label.remove(position);
        this.arr_lat.remove(position);
        this.arr_lon.remove(position);
    }

    public int getMarkerCount() {
        return this.arr_lat.size();
    }

    public void clearMarker() {
        arr_color = new ArrayList<String>();
        arr_label = new ArrayList<String>();
        arr_lat = new ArrayList<Double>();
        arr_lon = new ArrayList<Double>();
    }

    public Bitmap getMapByCenter(double latitude, double longitude
            , int zoom) {
        String URL = "http://maps.google.com/maps/api/staticmap?";
        URL += "center=" + String.valueOf(latitude)
                + "," + String.valueOf(longitude);
        URL += "&size=" + String.valueOf(width)
                + "x" + String.valueOf(height);
        URL += "&scale=" + String.valueOf(scale);
        URL += "&maptype=" + String.valueOf(mapType);
        URL += "&format=" + String.valueOf(format);
        URL += "&language=" + String.valueOf(language);
        URL += "&sensor=false";

        if(zoom != -1)
            URL += "&zoom=" + String.valueOf(zoom);

        for(int i = 0 ; i < arr_lat.size() ; i++) {
            URL += "&markers=";
            if(arr_color.get(i) != null)
                URL += "color:" + arr_color.get(i) + "|";
            if(!arr_label.get(i).equals(""))
                URL += "label:" + arr_label.get(i).toUpperCase() + "|";
            URL += arr_lat.get(i) + "," + arr_lon.get(i);
        }
        Log.i("Check", URL);

        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    public Bitmap getMapByMarker() {
        String URL = "http://maps.google.com/maps/api/staticmap?";
        URL += "size=" + String.valueOf(width)
                + "x" + String.valueOf(height);
        URL += "&scale=" + String.valueOf(scale);
        URL += "&maptype=" + String.valueOf(mapType);
        URL += "&format=" + String.valueOf(format);
        URL += "&language=" + String.valueOf(language);
        URL += "&sensor=false";

        for(int i = 0 ; i < arr_lat.size() ; i++) {
            URL += "&markers=";
            if(arr_color.get(i) != null)
                URL += "color:" + arr_color.get(i) + "|";
            if(!arr_label.get(i).equals(""))
                URL += "label:" + arr_label.get(i) + "|";
            URL += arr_lat.get(i) + "," + arr_lon.get(i);
        }
        Log.i("Check", URL);

        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
