package com.kelasp1.uas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "uas_db.db";
    private static final int DATABASE_VERSION = 8; // Naikkan versi
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBeli = "CREATE TABLE Beli (" +
                "kode TEXT PRIMARY KEY, " +
                "nama TEXT, " +
                "satuan TEXT, " +
                "harga DOUBLE, " +
                "diskon DOUBLE, " +
                "stok INTEGER);"; // Kolom stok dipertahankan
        db.execSQL(createTableBeli);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Beli");
        onCreate(db);
    }
    public boolean insertDataBeli(String kode, String nama, String satuan, Double harga, Double diskon, int stok) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", kode);
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        cv.put("diskon", diskon);
        cv.put("stok", stok);
        long result = db.insert("Beli", null, cv);
        return result != -1;
    }
    public ArrayList<item_beli> readDatabeli() {
        ArrayList<item_beli> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Beli", null);
        if (cursor.moveToFirst()) {
            do {
                itemList.add(new item_beli(
                        cursor.getString(cursor.getColumnIndexOrThrow("kode")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                        cursor.getString(cursor.getColumnIndexOrThrow("satuan")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("harga")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("diskon")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("stok")) // Stok tetap ada
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }
    public boolean updateDatabeli(String kode, String nama, String satuan, Double harga, Double diskon, int stok) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        cv.put("diskon", diskon);
        cv.put("stok", stok);
        int result = db.update("Beli", cv, "kode = ?", new String[]{kode});
        return result > 0;
    }
    public boolean deleteDatabeli(String kode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Beli", "kode = ?", new String[]{kode}) > 0;
    }
}