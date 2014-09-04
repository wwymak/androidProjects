package org.mydex.testappjsonparser;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class MainActivity extends ListActivity {
    //    url to get info from androidhive
    private static String url = "http://api.androidhive.info/contacts/";
    // json node names
    private static final String DATA_CONTACTS = "contacts";
    private static final String DATA_ID = "id";
    private static final String DATA_NAME = "name";
    private static final String DATA_EMAIL = "email";
    private static final String DATA_ADDRESS = "address";
    private static final String DATA_GENDER = "gender";
    private static final String DATA_PHONE = "phone";
    private static final String DATA_MOBILE = "mobile";
    private static final String DATA_HOME = "home";
    private static final String DATA_OFFICE = "office";
    //array to store the contacdt list
    JSONArray contacts = null;

    ArrayList<HashMap<String, String>> contactList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<HashMap<String, String>>();
        ListView myList = getListView();

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{

        protected Void doInBackground(Void... arg0){
            HttpCaller caller = new HttpCaller();
            String jsonResult = caller.makeHttpCall(url,caller.GET);

            Log.d("json response:", jsonResult);
            if (jsonResult != null){
                try {
                    JSONObject jsonContacts = new JSONObject(jsonResult);

                    contacts = jsonContacts.getJSONArray(DATA_CONTACTS);

                    for (int i = 0; i < contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString(DATA_ID);
                        String name = c.getString(DATA_NAME);
                        String email = c.getString(DATA_EMAIL);
                        String address = c.getString(DATA_ADDRESS);
                        String gender = c.getString(DATA_GENDER);

                        JSONObject p = c.getJSONObject(DATA_PHONE);
                        String mobile = p.getString(DATA_MOBILE);
                        String office = p.getString(DATA_OFFICE);
                        String home = p.getString(DATA_HOME);

                        HashMap<String, String> temp = new HashMap<String, String>();
                        temp.put(DATA_ID, id);
                        temp.put(DATA_NAME, name);
                        temp.put(DATA_EMAIL, email);
                        temp.put(DATA_MOBILE, mobile);

                        contactList.add(temp);


                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
            else {
                Log.e("HTTP CALLER: ", "no data form url");
            }
            return null;
        }

        protected void onPostExecute(Void result){
            super.onPostExecute(result);

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] {DATA_NAME, DATA_EMAIL, DATA_MOBILE},
                    new int[] {R.id.name, R.id.email, R.id.mobile}
            );

            setListAdapter(adapter);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
