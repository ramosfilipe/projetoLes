package util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Criado por Filipe Ramos em 29/05/14 as 15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tempoManager";

    // Table Names
    private static final String TABLE_ATIVIDADE= "atividades";
    private static final String TABLE_TI = "tempo_investido";


    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // atividades Table - column names
    private static final String KEY_NOME = "nome";

    // tempo Investido Table - column names
    private static final String KEY_ID_ATIVIDADE = "id_atividade_que_ele_mapeia";
    private static final String KEY_TEMPO_INVESTIDO_MINUTO = "tempo_gasto_em_minuto";




    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_ATIVIDADE  = "CREATE TABLE "
            + TABLE_ATIVIDADE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOME
            + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // tempo investido table create statement
    private static final String CREATE_TABLE_TI = "CREATE TABLE " + TABLE_TI
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_ATIVIDADE + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME," + KEY_TEMPO_INVESTIDO_MINUTO + " INTEGER" + ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_ATIVIDADE);
        db.execSQL(CREATE_TABLE_TI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATIVIDADE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TI);

        // create new tables
        onCreate(db);
    }

}
