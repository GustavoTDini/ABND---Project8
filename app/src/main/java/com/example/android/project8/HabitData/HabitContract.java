package com.example.android.project8.HabitData;

import android.provider.BaseColumns;

/**
 * Classe de contrato para controlar o banco de dados de registro de hábitos, fiz uma tabela que
 * representa hábitos realizados em um dia, tem a coluna do nome, por exemplo "tomar a medicação", uma coluna que indica
 * se foi realizado, representado por um int que faz as vezes de um boolean, (0 para feito e 1 para não feito), e uma coluna
 * que indica quantas vezes foi realizado
 */

public final class HabitContract {

    public final static int HABIT_DONE = 0;
    public final static int HABIT_NOT_DONE = 1;

    private HabitContract(){}

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "habit";
        public final static String COLUMN_HABIT_DONE_TODAY = "doneToday";
        public final static String COLUMN_HABIT_TIMES_DONE = "timesDone";

    }
}
