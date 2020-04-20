package id.nyaa.exercise0420;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DaftarKontak.db";
    public static final String KNK_TABLE_NAME = "kontak";
    public static final String KNK_COLUMN_ID = "id";
    public static final String KNK_COLUMN_NAMA = "nama";
    public static final String KNK_COLUMN_PHONE = "phone";
    public static final String KNK_COLUMN_EMAIL = "email";
    public static final String KNK_COLUMN_ALAMAT = "alamat";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table mahasiswa " +
                        "(id integer primary key, nim text,nama text,phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS kontak");
        onCreate(db);
    }

    public boolean insertContact(String nama, String phone, String email, String alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nama", nama);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("alamat", alamat);

        db.insert("kontak", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from kontak where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, KNK_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from kontak", null );
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(KNK_COLUMN_NAMA)));
            res.moveToNext();
        }
        return array_list;
    }
}

