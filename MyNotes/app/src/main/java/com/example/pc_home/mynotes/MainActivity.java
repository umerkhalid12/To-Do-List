package com.example.pc_home.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> Notes;
    SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.notes:
                Intent intent = new Intent(this, AddingNotes.class);
                intent.putExtra("Notes","");
                intent.putExtra("positionOfItem",-1 + "");
                startActivity(intent);
                return true;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Notes = new ArrayList<>();
      /*  sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.pc_home.mynotes;",Context.MODE_PRIVATE);
        try {

            Notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Notes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        listView = (ListView)findViewById(R.id.Notes_view);

        GetListView();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Notes.remove(position);
                                UpdateListView();

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = Notes.get(position);
                Intent intent = new Intent(getApplicationContext(), AddingNotes.class);
                intent.putExtra("Notes", value);
                intent.putExtra("positionOfItem",position + "");

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        //Intent intent = getIntent();
        //String newNotees = intent.getStringExtra("Notes");
        //Notes.add(newNotees);
        UpdateListView();
        super.onResume();
    }

    public void UpdateListView(){

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Notes);
            listView.setAdapter(arrayAdapter);
        try {
            sharedPreferences.edit().putString("Notes",ObjectSerializer.serialize(Notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void GetListView(){
         sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.pc_home.mynotes;",Context.MODE_PRIVATE);
        try {
            Notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Notes", ObjectSerializer.serialize(new ArrayList<String>())));
            UpdateListView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
