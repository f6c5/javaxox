package com.example.xox;

import static android.os.SystemClock.currentThreadTimeMillis;
import static android.os.SystemClock.sleep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xox.data.DatabaseHelper;
import com.example.xox.model.playHistory;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private DatabaseHelper db;
    public static int gMod = 0;
    private Boolean turn;

    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button btn_menu;
    private Button btn_restart;
    private Button btn_main;
    private Button w_new;
    private Button w_main;
    private Button[] buttons = new Button[9];

    private LinearLayout pnl_menu;
    private LinearLayout pnl_win_btn;

    private Boolean is_menu_active;

    private TextView stat_x;
    private TextView stat_o;
    private TextView tv_winner;

    private int xPoint = 0;
    private int oPoint = 0;
    private char winner;
    private int clickCount = 0;

    playHistory nowh;

    private final char[] board = new char[9];

    private DatabaseHelper db_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = new DatabaseHelper(this);
        nowh = new playHistory();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nowh.set_x_name(extras.getString("x_ad"));
            nowh.set_o_name(extras.getString("o_ad"));
            nowh.set_mod(extras.getString("g_mod"));
        }

        b1 = (Button)findViewById(R.id.btn_1);
        b2 = (Button)findViewById(R.id.btn_2);
        b3 = (Button)findViewById(R.id.btn_3);
        b4 = (Button)findViewById(R.id.btn_4);
        b5 = (Button)findViewById(R.id.btn_5);
        b6 = (Button)findViewById(R.id.btn_6);
        b7 = (Button)findViewById(R.id.btn_7);
        b8 = (Button)findViewById(R.id.btn_8);
        b9 = (Button)findViewById(R.id.btn_9);
        turn = true;
        is_menu_active = false;


        pnl_menu = (LinearLayout) findViewById(R.id.pnl_menu);
        pnl_win_btn = (LinearLayout) findViewById(R.id.pnl_win_btn);
        btn_menu = (Button) findViewById(R.id.btn_menu);
        btn_restart = (Button) findViewById(R.id.btn_restart);
        btn_main = (Button) findViewById(R.id.btn_main);
        w_main = (Button) findViewById(R.id.w_main);
        w_new = (Button) findViewById(R.id.w_new);


        btn_menu.setOnClickListener (view -> clickMenu());
        btn_restart.setOnClickListener (view -> clickRestar());
        btn_main.setOnClickListener (view -> goMain());
        w_main.setOnClickListener (view -> goMain());
        w_new.setOnClickListener (view -> newGame());
        db = new DatabaseHelper(this);

        b1.setOnClickListener (view -> clickBtn(b1,1));
        b2.setOnClickListener (view -> clickBtn(b2,2));
        b3.setOnClickListener (view -> clickBtn(b3,3));
        b4.setOnClickListener (view -> clickBtn(b4,4));
        b5.setOnClickListener (view -> clickBtn(b5,5));
        b6.setOnClickListener (view -> clickBtn(b6,6));
        b7.setOnClickListener (view -> clickBtn(b7,7));
        b8.setOnClickListener (view -> clickBtn(b8,8));
        b9.setOnClickListener (view -> clickBtn(b9,9));
        stat_x = (TextView) findViewById(R.id.stat_x);
        stat_o = (TextView) findViewById(R.id.stat_o);
        tv_winner = (TextView) findViewById(R.id.tv_winner);

        buttons[0] = b1;
        buttons[1] = b2;
        buttons[2] = b3;
        buttons[3] = b4;
        buttons[4] = b5;
        buttons[5] = b6;
        buttons[6] = b7;
        buttons[7] = b8;
        buttons[8] = b9;

        clickRestar();
    }

    public void goMain() {

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        save();
    }

    private void save(){

        nowh.set_score("X: " + xPoint + "  O: " + oPoint);
        playHistory ph = new playHistory(nowh.get_x_name(),nowh.get_o_name(),nowh.get_score(),nowh.get_mod());
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        long id = db.insertHistory(ph);

        if (id == -1){
            Toast.makeText(this, "Kayıt İşleminde Bir Hata Oluştu.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "kaydedildi.", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }



    }

    public void clickMenu(){
        if (!is_menu_active){
            is_menu_active = true;
            pnl_menu.setVisibility(View.VISIBLE);
        }
        else {
            is_menu_active = false;
            pnl_menu.setVisibility(View.GONE);
        }
    }

    public void clickRestar() {
        newGame();
        pnl_menu.setVisibility(View.GONE);
    }

    public void newGame() {


        for (int i = 0; i < 9; i++){
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(Color.rgb(50,80,120));
            buttons[i].setTextColor(Color.WHITE);
            board[i] = 'f';
        }

        turn = true;
        pnl_win_btn.setVisibility(View.GONE);
        tv_winner.setVisibility(View.GONE);
        winner = 'f';
        clickCount = 0;
        stat_x.setText(nowh.get_x_name() + ": " + xPoint);
        stat_o.setText(nowh.get_o_name() + ": " + oPoint);
    }

    public void clickBtn(Button btn, int index) {

        if (turn && btn.getText() == "") {

            btn.setText("X");
            switch(index) {
                case 1:
                    board[0] = 'X';
                    break;
                case 2:
                    board[1] =  'X';
                    break;
                case 3:
                    board[2] =  'X';
                    break;
                case 4:
                    board[3] =  'X';
                    break;
                case 5:
                    board[4] =  'X';
                    break;
                case 6:
                    board[5] =  'X';
                    break;
                case 7:
                    board[6] =  'X';
                    break;
                case 8:
                    board[7] =  'X';
                    break;
                case 9:
                    board[8] =  'X';
                    break;
            }
            turn = false;

            clickCount++;
        }

        btn.setEnabled(false);
        control();

        if (gMod == 0 && winner == 'f') {

            if (!turn && btn.getText() == "") {

                btn.setText("O");
                switch (index) {
                    case 1:
                        board[0] = 'O';
                        break;
                    case 2:
                        board[1] = 'O';
                        break;
                    case 3:
                        board[2] = 'O';
                        break;
                    case 4:
                        board[3] = 'O';
                        break;
                    case 5:
                        board[4] = 'O';
                        break;
                    case 6:
                        board[5] = 'O';
                        break;
                    case 7:
                        board[6] = 'O';
                        break;
                    case 8:
                        board[7] = 'O';
                        break;
                    case 9:
                        board[8] = 'O';
                        break;
                }
                turn = true;
                clickCount++;
                control();
            }
        }

        else if (!turn && (gMod == 1 || gMod == 2) && winner == 'f') {
            gmPc();
        }

    }

    public void control(){

        if (board[0]=='X' && board[1]=='X' && board[2]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[3]=='X' && board[4]=='X' && board[5]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[6]=='X' && board[7]=='X' && board[8]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[0]=='X' && board[3]=='X' && board[6]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[1]=='X' && board[4]=='X' && board[7]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[2]=='X' && board[5]=='X' && board[8]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[2]=='X' && board[4]=='X' && board[6]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[0]=='X' && board[4]=='X' && board[8]=='X'){
            xPoint +=1;
            winner = 'X';
        }
        else if (board[0]=='O' && board[1]=='O' && board[2]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[3]=='O' && board[4]=='O' && board[5]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[6]=='O' && board[7]=='O' && board[8]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[0]=='O' && board[3]=='O' && board[6]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[1]=='O' && board[4]=='O' && board[7]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[2]=='O' && board[5]=='O' && board[8]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[2]=='O' && board[4]=='O' && board[6]=='O'){
            oPoint +=1;
            winner = 'O';
        }
        else if (board[0]=='O' && board[4]=='O' && board[8]=='O'){
            oPoint +=1;
            winner = 'O';
        }

        if (winner == 'X'){

            tv_winner.setText("X kazandı");
            haveWinner();
        }
        if (winner == 'O'){

            tv_winner.setText("O kazandı");
            haveWinner();
        }
        if (clickCount == 9 && winner != 'O' && winner != 'X'){

            tv_winner.setText("Berabere");
            haveWinner();
        }

        stat_x.setText(nowh.get_x_name() + ": " + xPoint);
        stat_o.setText(nowh.get_o_name() + ": " + oPoint);

    }

    public void haveWinner(){

        pnl_win_btn.setVisibility(View.VISIBLE);
        tv_winner.setVisibility(View.VISIBLE);

        clickCount = 0;
        turn = true;
        for (int i = 0; i < 9; i++){
            buttons[i].setEnabled(false);
        }
    }

    public void gmPc(){

        if (board[0]=='O' && board[1]=='O' && board[2] == 'f'){
            pcSelect(2);
        }
        else if (board[0]=='O' && board[1]=='f' && board[2] == 'O'){
            pcSelect(1);
        }
        else if (board[0]=='f' && board[1]=='O' && board[2] == 'O'){
            pcSelect(0);
        }

        else if (board[3]=='O' && board[4]=='O' && board[5] == 'f'){
            pcSelect(5);
        }
        else if (board[3]=='O' && board[4]=='f' && board[5] == 'O'){
            pcSelect(4);
        }
        else if (board[3]=='f' && board[4]=='O' && board[5] == 'O'){
            pcSelect(3);
        }

        else if (board[6]=='O' && board[7]=='O' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[6]=='O' && board[7]=='f' && board[8] == 'O'){
            pcSelect(7);
        }
        else if (board[6]=='f' && board[7]=='O' && board[8] == 'O'){
            pcSelect(6);
        }


        else if (board[0]=='O' && board[3]=='O' && board[6] == 'f'){
            pcSelect(6);
        }
        else if (board[0]=='O' && board[3]=='f' && board[6] == 'O'){
            pcSelect(3);
        }
        else if (board[0]=='f' && board[3]=='O' && board[6] == 'O'){
            pcSelect(0);
        }

        else if (board[1]=='O' && board[4]=='O' && board[7] == 'f'){
            pcSelect(7);
        }
        else if (board[1]=='O' && board[4]=='f' && board[7] == 'O'){
            pcSelect(4);
        }
        else if (board[1]=='f' && board[4]=='O' && board[7] == 'O'){
            pcSelect(1);
        }

        else if (board[2]=='O' && board[5]=='O' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[2]=='O' && board[5]=='f' && board[8] == 'O'){
            pcSelect(5);
        }
        else if (board[2]=='f' && board[5]=='O' && board[8] == 'O'){
            pcSelect(2);
        }


        else if (board[0]=='O' && board[4]=='O' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[0]=='O' && board[4]=='f' && board[8] == 'O'){
            pcSelect(4);
        }
        else if (board[0]=='f' && board[4]=='O' && board[8] == 'X'){
            pcSelect(0);
        }

        else if (board[2]=='O' && board[4]=='O' && board[6] == 'f'){
            pcSelect(6);
        }
        else if (board[2]=='O' && board[4]=='f' && board[6] == 'O'){
            pcSelect(4);
        }
        else if (board[2]=='f' && board[4]=='O' && board[6] == 'O'){
            pcSelect(2);
        }

        /*xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx*/


        else if (board[0]=='X' && board[1]=='X' && board[2] == 'f'){
            pcSelect(2);
        }
        else if (board[0]=='X' && board[1]=='f' && board[2] == 'X'){
            pcSelect(1);
        }
        else if (board[0]=='f' && board[1]=='X' && board[2] == 'X'){
            pcSelect(0);
        }

        else if (board[3]=='X' && board[4]=='X' && board[5] == 'f'){
            pcSelect(5);
        }
        else if (board[3]=='X' && board[4]=='f' && board[5] == 'X'){
            pcSelect(4);
        }
        else if (board[3]=='f' && board[4]=='X' && board[5] == 'X'){
            pcSelect(3);
        }

        else if (board[6]=='X' && board[7]=='X' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[6]=='X' && board[7]=='f' && board[8] == 'X'){
            pcSelect(7);
        }
        else if (board[6]=='f' && board[7]=='X' && board[8] == 'X'){
            pcSelect(6);
        }


        else if (board[0]=='X' && board[3]=='X' && board[6] == 'f'){
            pcSelect(6);
        }
        else if (board[0]=='X' && board[3]=='f' && board[6] == 'X'){
            pcSelect(3);
        }
        else if (board[0]=='f' && board[3]=='X' && board[6] == 'X'){
            pcSelect(0);
        }

        else if (board[1]=='X' && board[4]=='X' && board[7] == 'f'){
            pcSelect(7);
        }
        else if (board[1]=='X' && board[4]=='f' && board[7] == 'X'){
            pcSelect(4);
        }
        else if (board[1]=='f' && board[4]=='X' && board[7] == 'X'){
            pcSelect(1);
        }

        else if (board[2]=='X' && board[5]=='X' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[2]=='X' && board[5]=='f' && board[8] == 'X'){
            pcSelect(5);
        }
        else if (board[2]=='f' && board[5]=='X' && board[8] == 'X'){
            pcSelect(2);
        }


        else if (board[0]=='X' && board[4]=='X' && board[8] == 'f'){
            pcSelect(8);
        }
        else if (board[0]=='X' && board[4]=='f' && board[8] == 'X'){
            pcSelect(4);
        }
        else if (board[0]=='f' && board[4]=='X' && board[8] == 'X'){
            pcSelect(0);
        }

        else if (board[2]=='X' && board[4]=='X' && board[6] == 'f'){
            pcSelect(6);
        }
        else if (board[2]=='X' && board[4]=='f' && board[6] == 'X'){
            pcSelect(4);
        }
        else if (board[2]=='f' && board[4]=='X' && board[6] == 'X'){
            pcSelect(2);
        }

        /*oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo*/

        else if (gMod == 1){
            gmPcEasy();
        }
        else if (gMod == 2){
            gmPcHard();
        }
    }

    public void gmPcEasy(){
        Random random;
        int rrr;
        while (true) {
            random = new Random(currentThreadTimeMillis());
            rrr = random.nextInt(1000) % 9;

            if (board[rrr] == 'f') {
                pcSelect(rrr);
                break;
            }

        }
    }

    public void gmPcHard(){
        Random random;
        int rrr;

        if (board[4] == 'f'){
            pcSelect(4);
        }
        else if(board[1] == 'X' && board[5] == 'X' && board[0] == 'f' && board[2] == 'f' && board[8] == 'f'){
            pcSelect(2);
        }
        else if(board[5] == 'X' && board[7] == 'X' && board[2] == 'f' && board[8] == 'f' && board[6] == 'f'){
            pcSelect(8);
        }
        else if(board[7] == 'X' && board[3] == 'X' && board[8] == 'f' && board[6] == 'f' && board[0] == 'f'){
            pcSelect(6);
        }
        else if(board[3] == 'X' && board[1] == 'X' && board[6] == 'f' && board[0] == 'f' && board[2] == 'f'){
            pcSelect(0);
        }

        else if(board[4] == 'O'){
            while (true) {
                random = new Random(currentThreadTimeMillis());
                rrr = random.nextInt(1000) % 9;
                if (board[rrr] == 'f' && (rrr == 1 || rrr == 3 || rrr == 5 || rrr == 7)) {
                    pcSelect(rrr);
                    break;
                }

            }
        }
        else if(board[4] == 'X'){
            while (true) {
                random = new Random(currentThreadTimeMillis());
                rrr = random.nextInt(1000) % 9;
                if (board[rrr] == 'f' && (rrr == 0 || rrr == 2 || rrr == 6 || rrr == 8)) {
                    pcSelect(rrr);
                    break;
                }

            }
        }

        else {
            while (true) {
                random = new Random(currentThreadTimeMillis());
                rrr = random.nextInt(1000) % 9;
                if (board[rrr] == 'f') {
                    pcSelect(rrr);
                    break;
                }
            }
        }
    }

    public void pcSelect(int ss){
        final Handler handler = new Handler();

        for (int i = 0; i < 9; i++){
            buttons[i].setEnabled(false);
        }

        handler.postDelayed(() -> {
            board[ss] = 'O';
            buttons[ss].setText("O");
            buttons[ss].setEnabled(false);
            turn = true;
            clickCount++;
            for (int i = 0; i < 9; i++){
                if (board[i] == 'f'){
                    buttons[i].setEnabled(true);
                }
            }
            control();
        }, 500);

    }

}