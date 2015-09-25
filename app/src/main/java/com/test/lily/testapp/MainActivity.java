package com.test.lily.testapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.test.lily.testapp.files.DatabaseActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void openCameraApp(View view) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_CAMERA_BUTTON);
        i.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CAMERA));
        sendOrderedBroadcast(i, null);
    }

    public void goToCrystalMaidenWiki(View view) {
        Uri webPage = Uri.parse("http://dota2.gamepedia.com/Crystal_Maiden");
        Intent i = new Intent(Intent.ACTION_VIEW, webPage);
        startActivity(i);
    }

    public void goToPreferencesActivity(View view) {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    public void goToFileHandlingActivity(View view) {
        Intent i = new Intent(this, FileHandlingActivity.class);
        startActivity(i);
    }

    public void goToFileDatabaseActivity(View view) {
        Intent i = new Intent(this, DatabaseActivity.class);
        startActivity(i);
    }

    public void goToStuffInBackgroundActivity(View view) {
        Intent i = new Intent(this, StuffInBackground.class);
        startActivity(i);
    }
}
