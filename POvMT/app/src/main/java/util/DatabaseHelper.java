package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import boleiros.povmt.app.model.Atividade;
import boleiros.povmt.app.model.TempoInvestido;

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

    // ------------------------ "atividade" table methods ----------------//

    /**
     * Creating a ativ
     */
    public long createAtividade(Atividade ativ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, ativ.getNome());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long atividade_id = db.insert(TABLE_ATIVIDADE, null, values);

        return atividade_id;
    }

    /**
     * get single atividade
     */
    public Atividade getAtividade(long todo_id) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ATIVIDADE + " WHERE "
                + KEY_ID + " = " + todo_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Atividade td = new Atividade();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNome((c.getString(c.getColumnIndex(KEY_NOME))));
        td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return td;
    }
    /**
     * get single atividade by name
     */
    public Atividade getAtividadeByName(String nome) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ATIVIDADE + " WHERE "
                + KEY_NOME + " = " + nome;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Atividade td = new Atividade();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNome((c.getString(c.getColumnIndex(KEY_NOME))));
        td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return td;
    }

    /**
     * activity exists?
     */
    public Boolean isActivityOnDB(String nome) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ATIVIDADE + " WHERE "
                + KEY_NOME + " = " + nome;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c == null)
            return false;
        return true;
    } 


    /**
     * getting all Atividades
     * */
    public List<Atividade> getAllAtividades() throws Exception {
        List<Atividade> todos = new ArrayList<Atividade>();
        String selectQuery = "SELECT  * FROM " + TABLE_ATIVIDADE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Atividade td = new Atividade();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNome((c.getString(c.getColumnIndex(KEY_NOME))));
                td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to
                todos.add(td);
            } while (c.moveToNext());
        }

        return todos;
    }



    /**
     * quantas atividades tem cadastradas
     */
    public int getAtividadeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ATIVIDADE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * Updating ativade
     */
    public int updateAtividade(Atividade todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, todo.getNome());

        // updating row
        return db.update(TABLE_ATIVIDADE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    /**
     * Deleting atividade
     */
    public void deleteAtividade(long atividade_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATIVIDADE, KEY_ID + " = ?",
                new String[] { String.valueOf(atividade_id) });
    }

    // ------------------------ "TI" table methods ----------------//

    /**
     * Creating TI
     */
    public long createTI(TempoInvestido ti, long id_atividade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_ATIVIDADE, id_atividade);
        values.put(KEY_CREATED_AT, getDateTime());
        values.put(KEY_TEMPO_INVESTIDO_MINUTO, ti.getTempoInvestidoMinuto());

        // insert row
        long ti_id = db.insert(TABLE_TI, null, values);

        return ti_id;
    }

    /**
     * getting all ti
     * */
    public List<TempoInvestido> getAllTi() throws Exception {
        List<TempoInvestido> tis = new ArrayList<TempoInvestido>();
        String selectQuery = "SELECT  * FROM " + TABLE_TI;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                TempoInvestido t = new TempoInvestido();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setIdAtividade(c.getInt(c.getColumnIndex(KEY_ID_ATIVIDADE)));
                t.setTempoInvestidoMinuto(c.getInt(c.getColumnIndex(KEY_TEMPO_INVESTIDO_MINUTO)));
                // adding to ti list
                tis.add(t);
            } while (c.moveToNext());
        }
        return tis;
    }

    /**
     * Updating a ti
     */
    public int updateTag(TempoInvestido ti) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPO_INVESTIDO_MINUTO, ti.getTempoInvestidoMinuto());
        values.put(KEY_ID_ATIVIDADE, ti.getIdAtividade());


        // updating row
        return db.update(TABLE_TI, values, KEY_ID + " = ?",
                new String[] { String.valueOf(ti.getId()) });
    }

    /**
     * Deleting a ti
     */
    public void deleteTI(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TI, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

    }
  SQLiteDatabase db = this.getWritableDatabase();


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}