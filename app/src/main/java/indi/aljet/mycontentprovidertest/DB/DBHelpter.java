package indi.aljet.mycontentprovidertest.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LJL-lenovo on 2017/7/3.
 */

public class DBHelpter extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";

    public static final String BOOK_TABLE_NAME = "book";

    public static final String USER_TABLE_NAME = "user";

    private static final int DB_VERSION = 3;

    private String CREATE_BOOK_TABLE = "CREATE TABLE IF" +
            " NOT EXISTS "+ BOOK_TABLE_NAME + "(_id INTEGER " +
            "PRIMARY KEY, "+ "name TEXT)";

    private String CREATE_USER_TABLE = "CREATE TABLE IF" +
            " NOT EXISTS " + USER_TABLE_NAME+ "(_id INTEGER" +
            " PRIMARY KEY,"+" name TEXT,"+ " sex INT)";



    public DBHelpter(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}