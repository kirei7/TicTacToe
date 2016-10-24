package com.vntu.julia.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vntu.julia.tictactoe.game.Game;
import com.vntu.julia.tictactoe.game.Player;
import com.vntu.julia.tictactoe.game.Square;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private Button[][] buttons = new Button[3][3];
    private TableLayout layout;
    private Player activePlayer;


    public MainActivity() {
        game = new Game();
        game.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout =  (TableLayout)findViewById(R.id.layout_table);
        buildGameField();
    }

    private void buildGameField() {
        Square[][] field = game.getField();
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this); // СЃРѕР·РґР°РЅРёРµ СЃС‚СЂРѕРєРё С‚Р°Р±Р»РёС†С‹
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j)); // СѓСЃС‚Р°РЅРѕРІРєР° СЃР»СѓС€Р°С‚РµР»СЏ, СЂРµР°РіРёСЂСѓСЋС‰РµРіРѕ РЅР° РєР»РёРє РїРѕ РєРЅРѕРїРєРµ
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT)); // РґРѕР±Р°РІР»РµРЅРёРµ РєРЅРѕРїРєРё РІ СЃС‚СЂРѕРєСѓ С‚Р°Р±Р»РёС†С‹
                /*button.setWidth(107);
                button.setHeight(107);*/
            }
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT)); // РґРѕР±Р°РІР»РµРЅРёРµ СЃС‚СЂРѕРєРё РІ С‚Р°Р±Р»РёС†Сѓ
            activePlayer = game.getCurrentActivePlayer();
            displayNextActivePlayerName(activePlayer);
        }
    }

    public class Listener implements View.OnClickListener {
        private int x = 0;
        private int y = 0;

        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            Button button = (Button) view;
            Game g = game;
            if (makeTurn(x, y)) {
                button.setText(activePlayer.getMark());
            }
            Player winner = g.checkWinner();
            if (winner != null) {
                gameOver(winner);
            }
            if (g.isFieldFilled()) {  // РІ СЃР»СѓС‡Р°Рµ, РµСЃР»Рё РїРѕР»Рµ Р·Р°РїРѕР»РЅРµРЅРѕ
                gameOver();
            }
            activePlayer = g.getCurrentActivePlayer();
            displayNextActivePlayerName(activePlayer);
        }
    }

    private boolean makeTurn(int x, int y) {
        return game.makeTurn(x, y);
    }

    private void gameOver(Player player) {
        CharSequence text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }

    private void gameOver() {
        CharSequence text = "Draw";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        game.reset();
        refresh();
    }

    private void refresh() {
        Square[][] field = game.getField();

        for (int i = 0, len = field.length; i < len; i++) {
            for (int j = 0, len2 = field[i].length; j < len2; j++) {
                if (field[i][j].getPlayer() == null) {
                    buttons[i][j].setText("");
                } else {
                    buttons[i][j].setText(field[i][j].getPlayer().getName());
                }
            }
        }
    }
    private void displayNextActivePlayerName(Player player) {
        TextView playerNameContainer = (TextView) findViewById(R.id.playerName);
        playerNameContainer.setText(player.getName());
    }
}

