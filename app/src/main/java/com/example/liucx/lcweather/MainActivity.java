package com.example.liucx.lcweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient mClient = new OkHttpClient();
    private DownloadDataTask mDownloadTask = new DownloadDataTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadTask.execute(getURL("101010100"));
    }

    URL getURL(String id) {
        try {
            return new URL("http://www.weather.com.cn/data/cityinfo/" + id + ".html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class DownloadDataTask extends AsyncTask<URL, Integer, Boolean> {
        WeatherInfo all;

        protected Boolean doInBackground(URL... urls) {
            for (URL tmp : urls)
                try {
                    Log.d("liucxdebug", getNetData(tmp));
                    String retJson = getNetData(tmp);
                    Gson gson = new Gson();
                    all = gson.fromJson(retJson, WeatherInfo.class);
                    Log.d("this is ", "debug " + all.getWeatherinfo().getCity());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            ((TextView) findViewById(R.id.city)).setText(all.getWeatherinfo().getCity());
            ((TextView) findViewById(R.id.id)).setText(all.getWeatherinfo().getCityid());
            ((TextView) findViewById(R.id.ptime)).setText(all.getWeatherinfo().getPtime());
            ((TextView) findViewById(R.id.tmp1)).setText(all.getWeatherinfo().getTemp1());
            ((TextView) findViewById(R.id.tmp2)).setText(all.getWeatherinfo().getTemp2());
            ((TextView) findViewById(R.id.weather)).setText(all.getWeatherinfo().getWeather());
        }
    }


    String getNetData(URL url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
}
