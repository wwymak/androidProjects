package org.mydex.testappjsonparser;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by WendyMak on 03/09/2014.
 * Test to use Volley instead of the older way to call http request via apache module
 */
public class AppController extends Application{

    private RequestQueue myRequestQueue;
    private ImageLoader myImageLoader;



}
