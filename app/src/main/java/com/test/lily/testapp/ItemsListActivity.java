package com.test.lily.testapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.lily.testapp.files.Item;
import com.test.lily.testapp.files.MyTableSource;

import java.util.List;
import java.util.zip.Inflater;


public class ItemsListActivity extends AppCompatActivity {

    private MyTableSource myTableSource;
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        myTableSource = new MyTableSource(this);
        myTableSource.open();

        ListView itemsList = (ListView) findViewById(R.id.listOfItems);
        items = myTableSource.getAllItems();
        //ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);
        ArrayAdapter<Item> adapter = new CustomList(this, 0, items);
        itemsList.setAdapter(adapter);

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ItemsListActivity.this, "You clicked on " + items.get(position).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items_list, menu);
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

    class CustomList extends ArrayAdapter<Item> {

        private Context context;
        private List<Item> objects;

        public CustomList(Context context, int resource, List<Item> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Item item = objects.get(position);

            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.layout_list, null);

            TextView nameTv = (TextView) view.findViewById(R.id.nameTv);
            nameTv.setText(item.getName());

            TextView priceTv = (TextView) view.findViewById(R.id.priceTv);
            priceTv.setText(item.getPrice().toString());

            return view;
        }
    }
}
