package com.example.coursereg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.coursereg.User;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentManager.db";
    private static final String TABLE_USER = "Students";

    private static final String COLUMN_STUDENT_ID = "Id";
    private static final String COLUMN_STUDENT_YEAR = "Year";
    private static final String COLUMN_STUDENT_LNAME = "LName";
    private static final String COLUMN_STUDENT_FNAME = "FName";
    private static final String COLUMN_STUDENT_EMAIL = "Email";

    private String CREATE_USER_TABLE = "CREATE TABLE Students (Id INTEGER PRIMARY KEY AUTOINCREMENT, Year INTEGER, LName TEXT, FName TEXT, Email TEXT)";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);
    }

    public void addStudent(User student){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_LNAME, student.getlName());
        values.put(COLUMN_STUDENT_FNAME, student.getfName());
        values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
        values.put(COLUMN_STUDENT_YEAR, student.getYear());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<User> getAllStudents(){
        String[] columns = {
                COLUMN_STUDENT_ID,
                COLUMN_STUDENT_YEAR,
                COLUMN_STUDENT_LNAME,
                COLUMN_STUDENT_FNAME,
                COLUMN_STUDENT_EMAIL
        };
        String sortOrder = COLUMN_STUDENT_LNAME + " ASC";
        List<User> studentList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID))));
                user.setYear(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_YEAR)));
                user.setlName(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_LNAME)));
                user.setfName(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_FNAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));

                studentList.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public void updateStudent(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_YEAR, user.getYear());
        values.put(COLUMN_STUDENT_LNAME, user.getlName());
        values.put(COLUMN_STUDENT_FNAME, user.getfName());
        values.put(COLUMN_STUDENT_EMAIL, user.getEmail());

        db.update(TABLE_USER, values, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteStudent(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_STUDENT_ID + "=?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkStudent(String email){
        String[] columns = {COLUMN_STUDENT_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_STUDENT_EMAIL + "=?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }



}
