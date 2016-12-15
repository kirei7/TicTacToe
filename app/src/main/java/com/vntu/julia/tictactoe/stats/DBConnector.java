package com.vntu.julia.tictactoe.stats;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/*клас для спілкування з базою даних*/
public class DBConnector {

    // Данные базы данных и таблиц
    private static final String DATABASE_NAME = "simple.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "stats";

    // Название столбцов
    //у базі ми зберігатимемо лише 2 стовпчики даних - ім'я переможця
    //і дату перемоги
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DATE = "Date";

    // Номера столбцов
    private static final int NUM_COLUMN_NAME = 0;
    private static final int NUM_COLUMN_DATE = 1;

    //посилання на об'єкт що з'єднується з базою даних
    private SQLiteDatabase mDataBase;

    public DBConnector(Context context) {
        // открываем (или создаем и открываем) БД для записи и чтения
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    // Класс для создания БД
    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);	}
        //тут виконуємо SQL запит на створення таблиці
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DATE + " LONG PRIMARY KEY); ";
            db.execSQL(query);
        }

        //при оновленні бази знищуємо таблицю і створюємо заново
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    // Метод добавления строки в БД
    public long insert(StatsDTO stats) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_DATE, stats.getDate());
        cv.put(COLUMN_NAME, stats.getName());
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    // Метод удаления всех записей из БД
    //не використовується у нашій програмі, можеш видалити, як хочеш
    public int deleteAll() {
        return mDataBase.delete(TABLE_NAME, null, null);
    }

    // Метод выборки всех записей
    public ArrayList<StatsDTO> selectAll() {
        //отримуємо вказівник на об'єкт із результатами запиту на виборку всіх значень
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE);

        //список у якому зберігатимуться всі записи з бд
        ArrayList<StatsDTO> arr = new ArrayList<>();

        //переміщаємо вказівник на перший рядок
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            //беремо інфу з полів і записуємо її в ДТО(детальніше див в описі класу StatsDTO)
            do {
                long date = mCursor.getLong(NUM_COLUMN_DATE);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                arr.add(new StatsDTO(name, date));
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        return arr;
    }
}