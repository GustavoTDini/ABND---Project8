package com.example.android.project8.HabitData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.project8.HabitData.HabitContract.HabitEntry;

/**
 * Classe que inclui o helper da database de hábitos, e os métodos de mostra e inserção de hábitos
 */

public class HabitDdHelper extends SQLiteOpenHelper {

    private final static int DONE_HABIT = 0;
    private final static int NOT_DONE_HABIT = 1;

    // inicialização do HabitDbHelper para uso nos métodos de entrada e display
    private HabitDdHelper mDbHelper;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "habits.db";

    public HabitDdHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                        HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL," +
                        HabitEntry.COLUMN_HABIT_DONE_TODAY + " TEXT," +
                        HabitEntry.COLUMN_HABIT_TIMES_DONE + " INTEGER,";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Método para mostrar os hábitos realizados durante um dia, retorna uma string com um
     * linha representado cada linha de hábito da tabela, dizendo se foi realizado ou não e quantas vezes
     *
     * @return descrição dos hábito realizados durante o dia, se foi realizado ou não
     */
    private String displayHabitInfo() {

        // criacão de um stringbuilder para ser concatenado
        StringBuilder dbHabitDisplay = new StringBuilder();
        dbHabitDisplay.append("You have Done Today: \n\n");


        // cria e/ou abreum database para ser lido
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = new String[]{
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DONE_TODAY,
                HabitEntry.COLUMN_HABIT_TIMES_DONE};

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);

        try {

            int nameColumnIndex = cursor.getColumnIndex( HabitEntry.COLUMN_HABIT_NAME );
            int doneColumnIndex = cursor.getColumnIndex( HabitEntry.COLUMN_HABIT_DONE_TODAY );
            int timesDoneColumnIndex = cursor.getColumnIndex( HabitEntry.COLUMN_HABIT_TIMES_DONE );

            while (cursor.moveToNext()) {
                String currentName = cursor.getString( nameColumnIndex );
                int currentDone = cursor.getInt( doneColumnIndex );
                int currentTimesDone = cursor.getInt( timesDoneColumnIndex );
                // testa se o hábito foi realizado, se não foi, mostra uma sentença, com qual hábito não foi feito
                if (currentDone == NOT_DONE_HABIT) {
                    dbHabitDisplay.append( "You have not done ");
                    dbHabitDisplay.append(currentName);
                    dbHabitDisplay.append(" today.\n");
                } else {
                    // se foi realizado o hábito,  cria um sentença qom quantas vezes foi realizado
                    dbHabitDisplay.append( "You have done ");
                    dbHabitDisplay.append(currentName);
                    dbHabitDisplay.append( ", ");
                    dbHabitDisplay.append( currentTimesDone );
                    dbHabitDisplay.append(" today.\n");
                }

            }

        } finally {
            //fecha o cursor
            cursor.close();
        }
        // retorna o String gerado pelo StringBuilder dbHabitDisplay
        return dbHabitDisplay.toString();
    }

    /**
     * Método para adicionar um hábito ao dia
     *
     * @param habitName nome (descrição) do hábito.
     * @param habitDone true se foi realizado e false se não
     * @param habitsTimesDone numero de vezes realizado
     */
    public void addHabit(String habitName, boolean habitDone, int habitsTimesDone){


        int sqlHabitDone = NOT_DONE_HABIT;
        if (habitDone){
            sqlHabitDone = DONE_HABIT;
        }

        // cria e/ou abreum database para ser escrito
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitName);
        values.put(HabitEntry.COLUMN_HABIT_DONE_TODAY, sqlHabitDone);
        values.put(HabitEntry.COLUMN_HABIT_TIMES_DONE, habitsTimesDone );

        // Insert the new row, returning the primary key value of the new row
        long newRowID = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v( "HabitDbHelper.addHabit", "New Habit Id = " + newRowID );
    }

}
