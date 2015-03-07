package com.acadgild.employeerecords;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class EmployeeDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Employee Manager";
    private static final String TABLE_EMPLOYEES = "Employees";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_PHOTO = "photo";

    public EmployeeDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_AGE + " INTEGER,"
                + KEY_PHOTO + " BLOB" + ")";
        db.execSQL(CREATE_TABLE);
    }

    boolean checkDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEES, null, KEY_NAME, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void AddEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, employee.getName());
        values.put(KEY_AGE, employee.getAge());
        values.put(KEY_PHOTO, employee.getPhoto());
        db.insert(TABLE_EMPLOYEES, null, values);
        db.close();
    }

    Employee getEmployee(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EMPLOYEES, new String[] {KEY_NAME, KEY_AGE, KEY_PHOTO}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
                if (cursor != null)
                    cursor.moveToFirst();
                Employee employee = new Employee(id, cursor.getString(0),
                        Integer.parseInt(cursor.getString(1)), cursor.getBlob(2));

                return employee;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        onCreate(db);

    }
}
