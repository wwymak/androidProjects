package org.mydex.testappjsonparser;

/**
 * Created by WendyMak on 03/09/2014.
 * Make http requests: ideally should replace this with either Volley or
 * HttpURLConnection
 */

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HttpCaller {
    static String response = null;
    public final int GET = 1;
    public final int POST = 2;

    //constructor for the httpCaller class
    public HttpCaller(){

    }
    //put this in for the times when you don't want to pass in params
    public String makeHttpCall(String url, int method) {
        return this.makeHttpCall(url, method, null);
    }

    public String makeHttpCall(String url, int method, List<NameValuePair> params){
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;
            if(method == POST){
                HttpPost httpPost = new HttpPost(url);
                if(params != null){
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);
            }
            else if (method == GET){
                HttpGet httpGet = new HttpGet(url);
                if (params != null){
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                }

                httpResponse = httpClient.execute(httpGet);
            }

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return  response;
    }
}

