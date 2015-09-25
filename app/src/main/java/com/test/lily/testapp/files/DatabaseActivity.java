package com.test.lily.testapp.files;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.test.lily.testapp.ItemsListActivity;
import com.test.lily.testapp.MainActivity;
import com.test.lily.testapp.R;
import com.test.lily.testapp.files.MyTableSource;

public class DatabaseActivity extends AppCompatActivity {

    public static final String MESSAGES = "Messages";
    MyTableSource myTableSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        myTableSource = new MyTableSource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        myTableSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myTableSource.close();
    }

    public void insertItem(View view) {
        myTableSource.open();
        String itemName = ((EditText)(findViewById(R.id.itemNameEditText))).getText().toString();
        String itemPrice = ((EditText)(findViewById(R.id.itemPriceEditText))).getText().toString();

        Long itemId = myTableSource.insertItem(itemName, itemPrice);
        Log.i(MESSAGES, "Item inserted with item id: " + itemId.toString());

        Intent i = new Intent(this, ItemsListActivity.class);
        startActivity(i);
    }

    public void viewItems(View view) {
        Intent i = new Intent(this, ItemsListActivity.class);
        startActivity(i);
    }
}
