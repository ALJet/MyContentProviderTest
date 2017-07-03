package indi.aljet.mycontentprovidertest.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import indi.aljet.mycontentprovidertest.DB.DBHelpter;

/**
 * Created by LJL-lenovo on 2017/7/3.
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";

    private static final String AUTHORITY = "indi.aljet.mycontentprovidertest.content.BookProvider";

    public static final Uri BOOK_CONTENT_URI =
            Uri.parse("content://"
            +AUTHORITY+"/"+ DBHelpter.BOOK_TABLE_NAME);

    public static final Uri USER_CONTENT_URI =
            Uri.parse("content://"
                    +AUTHORITY+"/"+ DBHelpter.USER_TABLE_NAME);

    public static final int BOOK_URI_CODE = 0;

    public static final int USER_URI_CODE = 1;

    private static final UriMatcher
     sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,"book",
                BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user"
        ,USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate,current thread:"+ Thread.currentThread()
        .getName());
        mContext = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData(){
        mDb = new DBHelpter(mContext)
                .getWritableDatabase();
        mDb.execSQL("delete from "+
                DBHelpter.BOOK_TABLE_NAME);
        mDb.execSQL("delete from "+
                DBHelpter.USER_TABLE_NAME);
        mDb.execSQL("insert into book values" +
                "(3,'Android');");
        mDb.execSQL("insert into book values" +
                "(4,'Ios');");
        mDb.execSQL("insert into book values" +
                "(5,'Html5');");
        mDb.execSQL("insert into user values" +
                "(1,'jake',1);");
        mDb.execSQL("insert into user values" +
                "(2,'jasmine',0);");
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query,current thread:"+ Thread.currentThread()
                .getName());
        String table = getTableName(uri);
        if(table == null){
            throw new IllegalArgumentException
                    ("Unsupported URI: " + uri);
        }
        return mDb.query(table,projection,
                selection,selectionArgs,null,null,
                sortOrder,null);

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG,"getType,current thread:"+ Thread.currentThread()
                .getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG,"insert,current thread:"+ Thread.currentThread()
                .getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table,null,values);
        mContext.getContentResolver()
                .notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG,"delete,current thread:"+ Thread.currentThread()
                .getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table,
                selection,selectionArgs);
        if(count > 0){
            getContext().getContentResolver()
                    .notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG,"update,current thread:"+ Thread.currentThread()
                .getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table,values
        ,selection,selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }


    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DBHelpter
                        .BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName =
                        DBHelpter
                        .USER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
