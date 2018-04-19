package com.example.android.project8;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.project8.HabitData.HabitDdHelper;

public class MainActivity extends AppCompatActivity {

    // inicialização do HabitDbHelper para uso nos métodos de entrada e display
    private HabitDdHelper mDbHelper = new HabitDdHelper( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // incialização de parametros de teste
        String habit = "Brush teeth";
        Boolean habitDone = true;
        int habitTimesDone = 3;

        // Abertura dos db de escrita e leitura
        SQLiteDatabase writableDb = mDbHelper.getWritableDatabase();
        SQLiteDatabase readableDb = mDbHelper.getReadableDatabase();

        // Teste de Métodos de leitura e escrita, o método todayHabit utiliza o método de criação de cursor
        HabitDdHelper.addHabit( habit, habitDone, habitTimesDone, writableDb );
        String todayHabit = HabitDdHelper.displayHabitInfo( HabitDdHelper.createHabitCursor( readableDb ) );
        Log.d( "displayHabitInfo", "onCreate: " + todayHabit );

    }
}
