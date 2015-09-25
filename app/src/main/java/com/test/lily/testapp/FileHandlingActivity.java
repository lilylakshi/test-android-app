package com.test.lily.testapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileHandlingActivity extends AppCompatActivity {

    private static final String FILENAME = "external_ file";
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_handling);

        File externalDir = getExternalFilesDir(null);
        file = new File(externalDir, FILENAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_handling, menu);
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

    public void createFile(View view) {

        if(!checkStorageState()) {
            return;
        }
        try {
            //FileOutputStream fileOutputStream = openFileOutput("textFile.txt", MODE_PRIVATE);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String text = ((EditText)findViewById(R.id.et)).getText().toString();
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "File successfully created", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void readFile(View view) {

        if(!checkStorageState()) {
            return;
        }
        try {
            //FileInputStream fileInputStream = openFileInput("textFile.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            StringBuilder stringBuffer = new StringBuilder();

            while (bufferedInputStream.available() != 0) {
                stringBuffer.append((char)bufferedInputStream.read());
            }
            ((TextView)findViewById(R.id.contentTv)).setText(stringBuffer.toString());
            bufferedInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void createJsonFile(View view) {
        JSONArray jsonArray = new JSONArray();
        JSONObject item;
        String text;

        try {
            item = new JSONObject();
            item.put("name", "Reaver");
            item.put("price", 3000);
            jsonArray.put(item);

            item = new JSONObject();
            item.put("name", "Boots of Speed");
            item.put("price", 450);
            jsonArray.put(item);

            text = jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            text = e.toString();
        }

        try {
            FileOutputStream fileOutputStream = openFileOutput("dotaItems", MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJasonFile(View view) {
        StringBuilder stringBuilder;
        try {
            FileInputStream fileInputStream = openFileInput("dotaItems");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            stringBuilder = new StringBuilder();
            while(bufferedInputStream.available() != 0) {
                stringBuilder.append((char)bufferedInputStream.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
            stringBuilder = new StringBuilder();
            stringBuilder.append(e.toString());
        }

        StringBuilder sb2;
        try {
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            sb2 = new StringBuilder();

            for (int i=0; i<jsonArray.length(); i++) {
                sb2.append(jsonArray.getJSONObject(i).getString("name")).append(": ");
                sb2.append(jsonArray.getJSONObject(i).getString("price")).append("\n");
            }

        } catch (JSONException e) {
            sb2 = new StringBuilder();
            e.printStackTrace();
            sb2.append(e.toString());
        }

        TextView tv = (TextView)findViewById(R.id.contentTv);
        tv.setText(sb2.toString());
    }

    public boolean checkStorageState() {
        String state = Environment.getExternalStorageState();

        return (state.equals(Environment.MEDIA_MOUNTED));

    }
}
