package capstone2015project.bustool;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import static android.R.layout.simple_list_item_1;

public class BusstopDbActivity extends AppCompatActivity {
    ListView stopsListView;
    ArrayList<String> stopList;
    private ProgressBar spinner;
    SQLiteHelper BsDb;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstop_db);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        final Button GetJSON=(Button) findViewById(R.id.Button_GetJSON);
        GetJSON.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "http://data.foli.fi/gtfs/v0/stops";
                new ProcessJSON().execute(url);
            }
        });

        final RadioButton rButton0=(RadioButton) findViewById(R.id.radioButton0);
        rButton0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BsDb = new SQLiteHelper(BusstopDbActivity.this);
                ArrayList array_list = BsDb.getQuery("SELECT * FROM busstops WHERE bs_fav = 1");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BusstopDbActivity.this, android.R.layout.simple_list_item_1, array_list);
                stopsListView = (ListView) findViewById(R.id.DbView);
                stopsListView.setAdapter(arrayAdapter);
            }
        });

        final RadioButton rButton1=(RadioButton) findViewById(R.id.radioButton1);
        rButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BsDb = new SQLiteHelper(BusstopDbActivity.this);
                ArrayList array_list = BsDb.getAllBSs();
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(BusstopDbActivity.this, android.R.layout.simple_list_item_1, array_list);
                stopsListView = (ListView) findViewById(R.id.DbView);
                stopsListView.setAdapter(arrayAdapter);
                stopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        BsDb.addFav(BsDb.BsIdList.get(arg2));
                        rButton0.performClick();


                    }
                });
            }
        });

        final RadioButton rButton2=(RadioButton) findViewById(R.id.radioButton2);
        rButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BsDb = new SQLiteHelper(BusstopDbActivity.this);
                ArrayList array_list = BsDb.getQuery("SELECT * FROM busstops WHERE bs_fav = 1");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BusstopDbActivity.this, android.R.layout.simple_list_item_1, array_list);
                stopsListView = (ListView) findViewById(R.id.DbView);
                stopsListView.setAdapter(arrayAdapter);
                stopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        BsDb.delFav(BsDb.BsIdList.get(arg2));
                        rButton2.performClick();

                    }
                });
            }
        });


        BsDb = new SQLiteHelper(BusstopDbActivity.this);
        ArrayList array_list = BsDb.getQuery("SELECT * FROM busstops WHERE bs_fav = 1");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(BusstopDbActivity.this, android.R.layout.simple_list_item_1, array_list);
        stopsListView = (ListView) findViewById(R.id.DbView);
        stopsListView.setAdapter(arrayAdapter);
        stopsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        stopsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                stopsListView.setSelection(arg2);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BusstopDb Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://capstone2015project.bustool/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BusstopDb Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://capstone2015project.bustool/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class ProcessJSON extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            //String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            String stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream) {


            if (stream != null) {
                try {

                    JSONObject stopsObject = new JSONObject(stream);
                    // Get the JSONArray stops
                    JSONArray stopsArray = stopsObject.toJSONArray(stopsObject.names());


                    for (int i = 0; i < stopsArray.length(); i++) {
                        JSONObject stop = stopsArray.getJSONObject(i);

                        String stopNumber = (String) stopsObject.names().get(i);
                        String stopName = stop.getString("stop_name");
                        BsDb.insertBS(stopNumber, stopName);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } // if statement end
        } // onPostExecute() end
    } // ProcessJSON class end

}
