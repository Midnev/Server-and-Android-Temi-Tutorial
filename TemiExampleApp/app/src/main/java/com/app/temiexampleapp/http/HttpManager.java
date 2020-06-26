package com.app.temiexampleapp.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class HttpManager {
    String baseUrl = "http://192.168.0.0:8080";

    public HttpManager(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String line = "";
    public String getHttpRequest(String getUrl,String param) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            line = "";
            client.get(baseUrl + getUrl+param, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    Log.i("Http GET Request","START ============================");
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    line = new String(response);// response
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //on failure code
                }
            });


        }catch (Exception e){
            Log.e("Error Log",e.getMessage());
        }
        return line;
    }
    public String postHttpRequest(String postUrl,String param){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("key","val");// 파라미터
        line = "";
        try {
            client.post(baseUrl + postUrl + param, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    line = new String(responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //on fail
                }
            });
        }catch (Exception e){
            Log.e("Error Log",e.getMessage());
        }
        return line;
    }

}
