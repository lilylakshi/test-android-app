package com.test.lily.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;
import com.test.lily.testapp.files.FlowerAndBitmap;
import com.test.lily.testapp.files.FlowerJSONParser;
import com.test.lily.testapp.files.FlowerXMLParser;
import com.test.lily.testapp.files.HttpManager;
import com.test.lily.testapp.files.RequestPackage;
import com.test.lily.testapp.models.Flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


public class StuffInBackground extends ActionBarActivity {

    private LruCache<Integer, Bitmap> imageCache;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suff_in_background);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suff_in_background, menu);
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

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
            return false;
    }

    public void checkConnection(View view) {
        if(this.isOnline()) {
            Toast.makeText(this, "Connected to internet", Toast.LENGTH_LONG).show();

            MyTask task = new MyTask();
            task.execute("http://services.hanselandpetal.com/secure/flowers.json", "feeduser", "feedpassword");
        } else {
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
        }
    }

    public void makePackagedGetRequest(View view) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri("http://services.hanselandpetal.com/restful.php");

        Map<String, String> map = new HashMap<>();
        map.put("name", "Lilan");
        map.put("dota2alias", "Liliya");
        map.put("age", "22");

        requestPackage.setParams(map);

        MakeGetRequest getRequest = new MakeGetRequest();
        getRequest.execute(requestPackage);
    }

    class MyTask extends AsyncTask<String, String, List<Flower>> {

        @Override
        protected void onPreExecute() {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected List<Flower> doInBackground(String... params) {
            String feed =  HttpManager.getData(params[0], params[1], params[2]);
            return FlowerJSONParser.parseFeed(feed);
        }

        @Override
        protected void onPostExecute(List<Flower> flowerList) {

            ArrayAdapter<Flower> adapter = new MyAdapter(StuffInBackground.this, 0, flowerList);

            final List<Flower> finalList = flowerList;

            ListView listView = (ListView) findViewById(R.id.tv);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(StuffInBackground.this, FlowerDetails.class);
                    intent.putExtra("FlowerAndBitmap", new FlowerAndBitmap(finalList.get(position), imageCache.get(finalList.get(position).getProductId())));
                    startActivityForResult(intent, 1);
                }
            });

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    private class MyAdapter extends ArrayAdapter<Flower> {

        private List<Flower> flowerList;
        private Context context;

        public MyAdapter(Context context, int resource, List<Flower> objects) {
            super(context, resource, objects);
            this.context = context;
            this.flowerList = objects;

            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            int maxCache = maxMemory / 8;

            imageCache = new LruCache<>(maxCache);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.flower_layout, null);

            Flower flower = flowerList.get(position);

            TextView textView = (TextView) rowView.findViewById(R.id.textView);
            textView.setText(flower.getName());

            Bitmap bitmap = imageCache.get(flower.getProductId());
            final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
            if(bitmap != null) {
                //imageView.setImageBitmap(flower.getBitmap());
                imageView.setImageBitmap(bitmap);
            }
            else {
                //ImageLoader imageLoader = new ImageLoader();
                //imageLoader.execute(new FlowerAndView(flower, rowView));

                String imageUrl =  HttpManager.BASE_URL + flower.getPhoto();

                ImageRequest imageRequest =  new ImageRequest(imageUrl,
                        new Response.Listener<Bitmap>() {

                            @Override
                            public void onResponse(Bitmap bitmap) {
                                imageView.setImageBitmap(bitmap);
                            }
                        },
                        100, 100,
                        Bitmap.Config.ARGB_8888,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                               Toast.makeText(StuffInBackground.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                );
                requestQueue.add(imageRequest);
            }
            return rowView;
        }
    }

    class FlowerAndView {

        FlowerAndView(Flower flower, View view) {
            this.flower = flower;
            this.view = view;
        }

        Flower flower;
        View view;
        Bitmap bitmap;
    }

    class ImageLoader extends AsyncTask<FlowerAndView, FlowerAndView, FlowerAndView> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected FlowerAndView doInBackground(FlowerAndView... params) {

            FlowerAndView containter = params[0];
            Flower flower = containter.flower;

            try {

                    URL imageUrl = new URL(HttpManager.BASE_URL + flower.getPhoto());
                    HttpURLConnection con = client.open(imageUrl);
                    //InputStream inputStream = (InputStream) imageUrl.getContent();
                    InputStream inputStream = con.getInputStream();
                    containter.bitmap = BitmapFactory.decodeStream(inputStream);
                    //flower.setBitmap(bitmap);
                    //containter.bitmap = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return containter;
        }

        @Override
        protected void onPostExecute(FlowerAndView flowerAndView) {

            ImageView imageView = (ImageView) flowerAndView.view.findViewById(R.id.imageView);
            //imageView.setImageBitmap(flowerAndView.flower.getBitmap());
            imageView.setImageBitmap(flowerAndView.bitmap);
            ProgressBar progressBar = (ProgressBar) flowerAndView.view.findViewById(R.id.pictureProgress);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            imageCache.put(flowerAndView.flower.getProductId(), flowerAndView.bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == 1) {
            Toast.makeText( this, "You returned from a page about " + data.getStringExtra(Flower.NAME), Toast.LENGTH_LONG).show();
        }
    }

    class MakeGetRequest extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... params) {
            return HttpManager.getData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(StuffInBackground.this, s, Toast.LENGTH_LONG).show();
        }
    }
}
