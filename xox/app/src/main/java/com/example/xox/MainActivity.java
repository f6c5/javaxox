package com.example.xox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.btn_1);
        b2 = (Button)findViewById(R.id.btn_2);
        b3 = (Button)findViewById(R.id.btn_3);
        b4 = (Button)findViewById(R.id.btn_4);
        b5 = (Button)findViewById(R.id.btn_5);
        b6 = (Button)findViewById(R.id.btn_6);
        b7 = (Button)findViewById(R.id.btn_7);
        b8 = (Button)findViewById(R.id.btn_8);
        b9 = (Button)findViewById(R.id.btn_9);

        b1.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "mesaj", Toast.LENGTH_LONG).show();
            } } );
    }











    private static final int id_yeni_el = 0;
    private static final int id_geri = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(menu.NONE, id_yeni_el, 0, "yeni el");
        menu.add(menu.NONE, id_geri, 1, "ana menü");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case id_yeni_el:
                Toast.makeText(getApplicationContext(),"yeni el",Toast.LENGTH_LONG).show();
                return true;
            case id_geri:
                Toast.makeText(getApplicationContext(),"geri gel",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}