package com.example.pc_home.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.io.IOException;

public class AddingNotes extends AppCompatActivity {
    EditText editText;
    boolean NewNotes = true;
    String PositionOfItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_notes);

        editText = (EditText) findViewById(R.id.textView);
        Intent intent = getIntent();
        String value = intent.getStringExtra("Notes");
        PositionOfItem = intent.getStringExtra("positionOfItem");
        editText.setText(value);
    }

    @Override
    public void onBackPressed() {

        String notesWritten = editText.getText().toString();
        if(notesWritten.length() > 0) {
            int pos = Integer.parseInt(PositionOfItem);
            if(pos == -1){
                MainActivity.Notes.add(notesWritten);
            }
            else{
                MainActivity.Notes.set(pos,notesWritten);
            }

        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.pc_home.mynotes;",Context.MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("Notes",ObjectSerializer.serialize(MainActivity.Notes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   super.onBackPressed();
        finish();
    }
}
