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
                "create table kontak " +
                        "(id integer primary key, nama text,phone text,email text, alamat text)");
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

    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from kontak WHERE id='" + id + "'");
    }

    public void updateContact(String id, String nama, String noTelp, String alamat, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("phone", noTelp);
        values.put("email", email);
        values.put("alamat", alamat);

        db.execSQL("UPDATE kontak SET nama='"+nama+"', phone='"+noTelp+"', email='"+email+"', alamat='"+alamat+"' where id='"+id+"'");
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from kontak where id='" + id + "'", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, KNK_TABLE_NAME);
        return numRows;
    }

    public ArrayList<Contact> getAllData() {
        ArrayList<Contact> contactsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from kontak", null);

        while (res.moveToNext()) {
            String id = res.getString(0);
            String nama = res.getString(1);
            String nomor = res.getString(2);
            String email = res.getString(3);
            String alamat = res.getString(4);

            Contact newContact = new Contact(id, nama, nomor, email, alamat);
            contactsList.add(newContact);
        }
        return contactsList;
    }
}

