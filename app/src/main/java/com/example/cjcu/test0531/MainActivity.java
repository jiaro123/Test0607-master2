package com.example.cjcu.test0531;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private MyDBHelper helper;
    private RecyclerView recyclerView;

    @Override
    protected void onRestart() {
        super.onRestart();
        //mylist();
        myview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mylist();
        myview();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mylist() {
        list = findViewById(R.id.list);
        helper=MyDBHelper.getInstance(this);
        Cursor c = helper.getReadableDatabase()
                .query("exp",null,null,null,null,null,null);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.list_row,
                c,
                new String[] {"_id","cdate","info","amount"},
                new int[] {R.id.tv_id,R.id.tv_cdate,R.id.tv_info,R.id.tv_amount},
                1);
        list.setAdapter(simpleCursorAdapter);
    }

    private void myview(){
        recyclerView = findViewById(R.id.recyclerView);
        helper=MyDBHelper.getInstance(this);
        Cursor c = helper.getReadableDatabase()
                .query("exp",null,null,null,null,null,null);

        List<person> trans=new ArrayList<>();

        if(c.moveToNext()){
            do{
                person p =new person();
                p.setCdate(c.getString(c.getColumnIndex("cdate")));
                p.setInfo(c.getString(c.getColumnIndex("info")));
                p.setAmount(c.getInt(c.getColumnIndex("amount")));
                trans.add(p);
            }while(c.moveToNext());
        }


        TransactionAdapter adapter =new TransactionAdapter(trans);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
