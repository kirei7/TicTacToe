package com.vntu.julia.tictactoe;

import android.content.Intent;
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
import com.vntu.julia.tictactoe.stats.StatsDTO;
import com.vntu.julia.tictactoe.stats.StatsSaver;

import java.util.Date;

/*
* activity - грубо кажучи, екрани додатку, це клас головного екрану
* який показується після запуску програми(на ньому ігрове поле і кнопка перегляду статистики)
* в цій курсовій 2 екрани - цей і екран де показується статистика
* Отже, наслідуємо наш клас від AppCompatActivity, т.я. екран має бути
* саме типу Activity і містить деякі методи (наприклад для управління життєвим циклом - коли
* запускати, коли закривати і т.д.), уже прописані в SDK (бібліотека і не тільки для розробки програм під андроід)
* Важливо - цей клас є посередником між користувачем і самою грою, т.я. у ньому є графічний інтерфейс
* і правила "спілкування" з грою (він повідомляє гру про дії користувача і навпаки)
* */
public class MainActivity extends AppCompatActivity {

    // об'єкт, у якому міститься вся логіка, що стосується безпосередньо гри
    private Game game;
    //масив кнопок для поля гри
    private Button[][] buttons = new Button[3][3];
    //будемо розміщувати хрестики-нолики в таблиці
    private TableLayout layout;
    //тут будемо зберігати гравця, який має робити хід
    private Player activePlayer;
    //об'єкт для збереження статистики у базі даних
    private StatsSaver statsSaver;

    //при створенні екрану створюємо також логіку гри і запускаємо її
    public MainActivity() {
        game = new Game();
        game.start();
    }

    //одразу після створення екрану
    //override - значить ми переписуємо код цього методу нащадка
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //викликаємо цей же, але "непереписаний" метод у нащадка
        //там робиться щось, про що можна і не знати
        //далі я не буду повторювати цей коментар з іншими методами поміченими як @Override
        super.onCreate(savedInstanceState);
        //а це вже код який писали ми, його треба знати :)
        /*задаємо вигляд для цього екрану як layout з id="activity_main"
        * цей вигляд(а також інші) можна знайти у папці res/layout
        * ВАЖЛИВО - вигляд це сутність, яка представляє деякий графічний елемент
        * (таблицю, кнопку, текст, загалом все що може показуватись на екрані)*/
        setContentView(R.layout.activity_main);
        /*вигляд для хрестиків-ноликів, можна знайти у файлі activity_main
        * просто табличний вигляд, знаходимо його по id
        * ВАЖЛИВО - вигляди описуються xml-файлами, що зберігаються у папці res
        * андроід студіо сам індексує ці файли(та інші ресурси) і поміщає їх у клас R
        * потім ми можемо звертатись до них у програмі через цей клас, як,
        * наприклад, робимо це нижче - з класу R беремо елемент з id layout_table*/
        layout =  (TableLayout)findViewById(R.id.layout_table);
        /*так само задаємо вигляд для кнопки перегляду статистики,
        * задаємо об'єкт, який буде реагувати на натискання цієї кнопки*/
        Button statsButton = (Button) findViewById(R.id.stats);
        statsButton.setOnClickListener(new StatsButtonListener());
        statsSaver = new StatsSaver(this);
        buildGameField();
    }

    //створюємо і заповнюємо поле для гри
    private void buildGameField() {
        //отримуємо двувимірний масив квадратів, який представляє собою поле у грі
        Square[][] field = game.getField();
        //проходимо по кожному стовпцю масиву
        for (int i = 0, lenI = field.length; i < lenI; i++ ) {
            TableRow row = new TableRow(this); // створюємо строку(рядок) таблиці
            //проходимо по кожному елементу стовпця
            for (int j = 0, lenJ = field[i].length; j < lenJ; j++) {
                /*створюємо кнопку, заносимо її у масив кнопок,
                * що представляють поле в графічному інтерфейсі
                * задаємо кожній кнопці об'єкт, що реагуватиме на її натискання
                * та поміщаємо її у строку(рядок) таблиці*/
                Button button = new Button(this);
                buttons[i][j] = button;
                button.setOnClickListener(new Listener(i, j));
                row.addView(button, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
            }
            //поміщаємо строку(рядок) у таблицю
            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            //знаходимо гравця який робить перший хід
            activePlayer = game.getCurrentActivePlayer();
            //відображаємо його ім'я
            displayPlayerName(activePlayer);
        }
    }

    //клас що реагує на натискання кнопки ігрового поля (крч реагує на хід гравця)
    public class Listener implements View.OnClickListener {
        //координати кнопки яка прослуховується
        private int x = 0;
        private int y = 0;

        //при створенні задаємо ці координати
        public Listener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //при натисненні на кнопку
        public void onClick(View view) {
            //отримуємо кнопку яка була натиснена
            Button button = (Button) view;
            //намагаємось зробити хід - якщо хід не робиться(користувач уже натисну на квадрат
            // у якому походили) то вертається false,
            //весь інший код виконується але нічого не міняється
            if (game.makeTurn(x, y)) {
                button.setText(activePlayer.getMark());
            }
            //перевіряємо чи є переможець і якщо так то кінчаємо гру
            Player winner = game.checkWinner();
            if (winner != null) {
                gameOver(winner);
            }
            //якщо поле заповнене але немає переможця то кінчаємо гру
            if (game.isFieldFilled()) {
                gameOver();
            }
            //інакше отримуємо ім'я гравця, який має походити і відображаємо його
            activePlayer = game.getCurrentActivePlayer();
            displayPlayerName(activePlayer);
        }
    }

    //при перемозі одного з гравців
    private void gameOver(Player player) {
        //зберігаємо ім'я гравця та дату перемоги у базу даних
        statsSaver.persist( new StatsDTO(
                player.getName(),
                (new Date()).getTime()
        ));
        //також створюємо і недовго показуємо повідомлення про перемогу гравця
        CharSequence text = "Player \"" + player.getName() + "\" won!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        //скидаємо все до початкових значень
        game.reset();
        refresh();
    }

    //коли гра кінчається нічиєю просто виводимо про це повідомлення
    private void gameOver() {
        CharSequence text = "Draw";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        game.reset();
        refresh();
    }

    //скидаємо все до початкових значень(так же як і при створенні у onCreate()
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
    //показати ім'я гравця, який має походити
    private void displayPlayerName(Player player) {
        //беремо вигляд, у якому має показуватись ім'я гравця
        TextView playerNameContainer = (TextView) findViewById(R.id.playerName);
        //задаємо його текст - це саме ім'я
        playerNameContainer.setText(player.getName());
    }

    //клас який слідкує за натисненням кнопки статистики
    public class StatsButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /*Створюємо і запускаємо інтент для іншого екрану - екрану статистики
            * ВАЖЛИВО - інтент це така магічна штука, яка дозволяє запустити інший
            * екран, для цього йому необхідно мати посилання на клас звідки він прийшов
            * і куди йому треба прийти*/
            Intent intent = new Intent(
                    getThis(), //звідки
                    StatsActivity.class //куди(який екран запустити)
            );
            //запускаємо
            startActivity(intent);
        }

    }
    //отримання посилання на цей екран
    private MainActivity getThis() {
        return this;
    }
}

