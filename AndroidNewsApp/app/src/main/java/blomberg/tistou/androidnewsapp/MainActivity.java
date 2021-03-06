package blomberg.tistou.androidnewsapp;

import android.os.AsyncTask;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnFragmentInteractionListener{

    private static final String DEBUG_NEWS_TAG = "debugNews";
    private static final String JSON_NEWS_URL = "https://dl.dropboxusercontent.com/u/277040683/payload.json";

    public static ArrayList<NewsItem> newsItemArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetJsonNews getJsonNews = new GetJsonNews();
        getJsonNews.execute(JSON_NEWS_URL);

        createItemFragment();
    }

    private void createItemFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ItemFragment itemFragment = new ItemFragment();
        transaction.add(R.id.ACTIVITY_MAIN_RELATIVE_LAYOUT, itemFragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class GetJsonNews extends AsyncTask<String, Void, String> {

        URL url;
        HttpURLConnection connection;
        String jsonReturnString;

        @Override
        protected String doInBackground(String... params) {

            //Create URL
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //Create Connection
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Setup request
            try {
                connection.setRequestMethod(params[0]);
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setRequestProperty("Accept", "application/json");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            //Download
            try {
                connection.connect();

                InputStreamReader input = new InputStreamReader(connection.getInputStream());
                BufferedReader buffR = new BufferedReader(input);
                String inputLine;
                StringBuffer strBuff = new StringBuffer();

                while ((inputLine = buffR.readLine()) != null) {
                    strBuff.append(inputLine);
                }
                buffR.close();

                jsonReturnString = strBuff.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonReturnString;
        }

        @Override
        protected void onPostExecute(String s) {
            newsItemArray = JsonNewsConverter.JsonToNewsItems(s);
            Log.e(DEBUG_NEWS_TAG, newsItemArray.get(0).getTitle());
            Log.e(DEBUG_NEWS_TAG, newsItemArray.get(newsItemArray.size() - 1).getTitle());
        }
    }

    @Override
    public void onFragmentInteraction(String id) {
        Log.e(DEBUG_NEWS_TAG, "onFragmentInteraction id:" + id);
    }
}
