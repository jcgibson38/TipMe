package com.jcgibson.tipme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.Map;

/**
 * Created by Jordan on 7/22/2015.
 *
 * Methods to handle database operations.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shiftManager";
    private static final String TABLE_SHIFTS = "shifts";

    private static final String KEY_ID = "id";
    private static final String KEY_CASH = "cash";
    private static final String KEY_CREDIT = "credit";
    private static final String KEY_TIPOUT = "tipout";
    private static final String KEY_HOURS = "hours";
    private static final String KEY_DATE = "date";
    private static final String KEY_SALES = "sales";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_SHIFT_TABLE = "CREATE TABLE " + TABLE_SHIFTS + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_CASH + " REAL, " +
                                    KEY_CREDIT + " REAL, " + KEY_TIPOUT + " REAL, " + KEY_HOURS + " REAL, " + KEY_DATE + " REAL, " + KEY_SALES + " REAL " + ")";

        db.execSQL(CREATE_SHIFT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIFTS);
        onCreate(db);
    }

    public long addShift(Shift shift)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CASH, shift.getCashTips());
        values.put(KEY_CREDIT, shift.getCreditTips());
        values.put(KEY_TIPOUT, shift.getTipOut());
        values.put(KEY_HOURS, shift.getHoursWorked());
        values.put(KEY_DATE, shift.getDateAsLong());
        values.put(KEY_SALES, shift.getTotalSales());

        long id = db.insert(TABLE_SHIFTS, null, values);
        db.close();

        return id;
    }

    public Shift getShift(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] keyString = new String[]{KEY_ID, KEY_CASH, KEY_CREDIT, KEY_TIPOUT, KEY_HOURS, KEY_DATE, KEY_SALES};
        String[] idString = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(TABLE_SHIFTS, keyString, KEY_ID + "=?", idString, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        return new Shift(cursor);
    }

    public Map<Date, Shift> getAllMapShifts()
    {
        Map<Date, Shift> shiftMap = new android.support.v4.util.ArrayMap<>();

        String selectQuery = "SELECT * FROM " + TABLE_SHIFTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do
            {
                Shift shift = new Shift(cursor);
                shiftMap.put(shift.getDate(), shift);
            }
            while(cursor.moveToNext());
        }

        return shiftMap;
    }

    public void deleteShift(Shift shift)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("ID", "Id is " + String.valueOf(shift.getId()));
        db.delete(TABLE_SHIFTS, KEY_ID + " = ?", new String[]{String.valueOf(shift.getId())});
        db.close();
    }

    public int getShiftsCount()
    {
        String countQuery = "SELECT * FROM " + TABLE_SHIFTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        return cursor.getCount();
    }

    public int updateShift(Shift shift)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CASH, shift.getCashTips());
        values.put(KEY_CREDIT, shift.getCreditTips());
        values.put(KEY_TIPOUT, shift.getTipOut());
        values.put(KEY_HOURS, shift.getHoursWorked());
        values.put(KEY_DATE, shift.getDateAsLong());
        values.put(KEY_SALES, shift.getTotalSales());

        return db.update(TABLE_SHIFTS, values, KEY_ID + " = ?", new String[]{String.valueOf(shift.getId())});
    }
}
