package indi.aljet.mycontentprovidertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import indi.aljet.mycontentprovidertest.entity.Book;
import indi.aljet.mycontentprovidertest.entity.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Uri uri = Uri.parse("content://indi.aljet.mycontentprovidertest.content.BookProvider");
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);


        Uri bookUri = Uri
                .parse("content://indi.aljet.mycontentprovidertest.content.BookProvider/book");
        ContentValues values = new ContentValues();
        values.put("_id,",6);
        values.put("name","程序设计的艺术");
        getContentResolver()
                .insert(bookUri,
                        values);
        Cursor bookCursor =
                getContentResolver().query(bookUri,
                        new String[]{"_id","name"},
                        null,null,null);
        while(bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor
                    .getInt(0);
            book.bookName = bookCursor
                    .getString(1);
            Log.d(TAG, "query book:" + book.toString());
        }
        bookCursor.close();

        Uri userUri = Uri.parse
                ("content://indi.aljet.mycontentprovidertest.content.BookProvider/user");
        Cursor userCursor =
                getContentResolver().query(userUri
                ,new String[]{"_id","name","sex"},
                        null,null,null);
        while(userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor
                    .getString(1);
            user.isMale = userCursor.getInt(2)
                    == 1;
            Log.d(TAG, "query user:" + user.toString());
        }
        userCursor.close();



    }
}
