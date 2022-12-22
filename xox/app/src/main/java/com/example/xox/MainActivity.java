package com.example.xox;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xox.data.DatabaseHelper;
import com.example.xox.model.playHistory;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LinearLayout pnl_setting;
    private LinearLayout pnl_mod;
    private LinearLayout pnl_history;
    private Boolean is_setting_active;
    private Boolean is_help_active;

    private Button btn_start;
    private Button btn_setting;
    private Button btn_help;
    private Button btn_save;
    private Button btn_clear_hist;

    private RadioButton rd_easy;
    private RadioButton rd_hard;
    private RadioButton rd_human;
    private RadioButton rd_pc;
    private EditText et_x_name;
    private EditText et_o_name;
    private String game_mode;

    DatabaseHelper db;
    ListView listView;
    ArrayList<String> list_ph;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_start = (Button) findViewById(R.id.btn_start);
        btn_setting = (Button) findViewById(R.id.btn_settig);
        btn_help = (Button) findViewById(R.id.btn_history);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_clear_hist = (Button) findViewById(R.id.btn_clear_hist);

        pnl_history = (LinearLayout) findViewById(R.id.pnl_history);
        pnl_setting = (LinearLayout) findViewById(R.id.pnl_setting);
        pnl_mod = (LinearLayout) findViewById(R.id.pnl_mod);
        is_setting_active = false;
        is_help_active = false;

        rd_easy = (RadioButton)  findViewById(R.id.rd_easy);
        rd_hard = (RadioButton)  findViewById(R.id.rd_hard);
        rd_human = (RadioButton) findViewById(R.id.rd_human);
        rd_pc = (RadioButton) findViewById(R.id.rd_pc);
        et_x_name = (EditText) findViewById(R.id.et_x_nick);
        et_o_name = (EditText) findViewById(R.id.et_o_nick);

        btn_start.setOnClickListener (view -> clickStart());
        btn_setting.setOnClickListener (view -> clickSetting());
        btn_help.setOnClickListener (view -> clickHistory());
        btn_save.setOnClickListener (view -> clickSave());
        btn_clear_hist.setOnClickListener(view -> clickDelHistory());

        rd_pc.setOnClickListener(view -> checkedPc());
        rd_human.setOnClickListener(view -> checkedHuman());
        rd_easy.setOnClickListener(view -> checkedEasy());
        rd_hard.setOnClickListener(view -> checkedHard());

        game_mode = "insana_karsi";

        registerForContextMenu(btn_start);
    }

    public void clickStart() {
        Intent i = new Intent(MainActivity.this,GameActivity.class);
        i.putExtra("x_ad",String.valueOf(et_x_name.getText()));
        i.putExtra("o_ad",String.valueOf(et_o_name.getText()));
        i.putExtra("g_mod",game_mode);
        startActivity(i);
    }

    public void clickSetting() {
        if (!is_setting_active){
            pnl_setting.setVisibility(View.VISIBLE);
            pnl_history.setVisibility(View.GONE);
            is_help_active = false;
            is_setting_active = true;
        }
        else {
            pnl_setting.setVisibility(View.GONE);
            is_setting_active = false;
        }
    }

    public void clickHistory() {
        if (!is_help_active){
            pnl_history.setVisibility(View.VISIBLE);
            pnl_setting.setVisibility(View.GONE);
            is_setting_active = false;
            is_help_active = true;
            listHistory();
        }
        else {
            pnl_history.setVisibility(View.GONE);
            is_help_active = false;
        }
    }

    private void clickDelHistory() {
        db.deleteHistory();
        listHistory();
        Toast.makeText(MainActivity.this, "geçmiş silindi", Toast.LENGTH_SHORT).show();
    }

    private void listHistory() {
        listView = findViewById(R.id.lv_history);
        db = new DatabaseHelper(MainActivity.this);
        list_ph = new ArrayList<>();

        List<playHistory> List =db.listHistory();
        for(playHistory playHistory :List)
        {
            list_ph.add(playHistory.get_history());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_ph);
        listView.setAdapter(adapter);

    }

    public void clickSave() {
        pnl_setting.setVisibility(View.GONE);
        is_setting_active = false;
    }

    public void checkedPc() {
        pnl_mod.setVisibility(View.VISIBLE);
    }

    public void checkedHuman() {
        pnl_mod.setVisibility(View.GONE);
        game_mode = "insana_karsi";
        et_o_name.setText("oyuncu 2");
        rd_human.setChecked(true);
        GameActivity.gMod = 0;
    }

    public void checkedEasy() {
        checkedPc();
        game_mode ="pc_kolay";
        et_o_name.setText("PC");
        rd_pc.setChecked(true);
        rd_easy.setChecked(true);
        GameActivity.gMod = 1;
    }

    public void checkedHard() {
        checkedPc();
        game_mode = "pc_zor";
        GameActivity.gMod = 2;
        et_o_name.setText("PC");
        rd_pc.setChecked(true);
        rd_hard.setChecked(true);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, "İnsana Karşı");
        menu.add(0, v.getId(), 0, "Bilgisayara Karşı Kolay");
        menu.add(0, v.getId(), 0, "Bilgisayara Karşı Zor");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle() == "İnsana Karşı") {
            rd_human.setChecked(true);
            checkedHuman();
            clickStart();
        }
        else if (item.getTitle() == "Bilgisayara Karşı Kolay") {
            rd_pc.setChecked(true);
            rd_easy.setChecked(true);
            checkedEasy();
            clickStart();
        }
        else if (item.getTitle() == "Bilgisayara Karşı Zor") {
            rd_pc.setChecked(true);
            rd_hard.setChecked(true);
            checkedHard();
            clickStart();
        }

        return true;
    }

}