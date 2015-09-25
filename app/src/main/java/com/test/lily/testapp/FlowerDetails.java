package com.test.lily.testapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.lily.testapp.files.FlowerAndBitmap;
import com.test.lily.testapp.files.HttpManager;
import com.test.lily.testapp.models.Flower;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class FlowerDetails extends ActionBarActivity {

    Flower flower;
    FlowerAndBitmap container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_details);

        Intent intent = getIntent();
        container = intent.getParcelableExtra("FlowerAndBitmap");
        flower = container.getFlower();

        if(container.getBitmap() == null) {
            FlowerImageLoader imageLoader = new FlowerImageLoader();
            imageLoader.execute(container);
        }

        ImageView imageView = (ImageView) findViewById(R.id.flowerImage);
        imageView.setImageBitmap(container.getBitmap());

        TextView textView = (TextView) findViewById(R.id.nameTv);
        textView.setText(flower.getName());

        textView = (TextView) findViewById(R.id.descTv);
        textView.setText(flower.getInstructions());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flower_details, menu);
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

    public void returnToParent(View view) {
        String flowerName = ((TextView)findViewById(R.id.nameTv)).getText().toString();
        Intent intent = getIntent();
        intent.putExtra(Flower.NAME, flowerName);
        setResult(1, intent);
        finish();
    }

    class FlowerImageLoader extends AsyncTask<FlowerAndBitmap, FlowerAndBitmap, FlowerAndBitmap> {

        @Override
        protected FlowerAndBitmap doInBackground(FlowerAndBitmap... params) {

            FlowerAndBitmap containter = params[0];
            Flower flower = containter.getFlower();

            try {
                URL imageUrl = new URL(HttpManager.BASE_URL + flower.getPhoto());
                InputStream inputStream = (InputStream) imageUrl.getContent();
                containter.setBitmap(BitmapFactory.decodeStream(inputStream));
                //flower.setBitmap(bitmap);
                //containter.bitmap = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return containter;
        }

        @Override
        protected void onPreExecute() {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected void onPostExecute(FlowerAndBitmap FlowerAndBitmap) {

            ImageView imageView = (ImageView) findViewById(R.id.flowerImage);
            //imageView.setImageBitmap(flowerAndView.flower.getBitmap());
            imageView.setImageBitmap(FlowerAndBitmap.getBitmap());

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
