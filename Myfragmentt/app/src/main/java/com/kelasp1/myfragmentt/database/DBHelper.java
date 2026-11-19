package com.kelasp1.myfragmentt.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kelasp1.myfragmentt.itemjual.Item_jual;
import com.kelasp1.myfragmentt.itembeli.item_beli;
import com.kelasp1.myfragmentt.transaksijual.DetailTransaksi;
import com.kelasp1.myfragmentt.transaksijual.Item;
import com.kelasp1.myfragmentt.transaksijual.Transaksi;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 4;
    private static final String COL_IMAGE_PATH = "image_path";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Items(" +
                "kode TEXT PRIMARY KEY, " +
                "nama TEXT, " +
                "satuan TEXT, " +
                "harga Double," +
                "image_path TEXT NOT NULL " + ");";
        db.execSQL(createTable);

        String createTablebeli = "CREATE TABLE Beli (" +
                "kode TEXT PRIMARY KEY, " +
                "nama TEXT, " +
                "satuan TEXT, " +
                "harga Double)";
        db.execSQL(createTablebeli);

        String createTableTransaksi = "CREATE TABLE Transaksi (" +
                "kodetr TEXT PRIMARY KEY, " +
                "tanggal TEXT, " +
                "grandtotal DOUBLE);";
        db.execSQL(createTableTransaksi);

        // Tabel DetailTransaksi
        String createTableDetail = "CREATE TABLE DetailTransaksi (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kodetr TEXT, " +
                "kode TEXT, " +
                "qty INTEGER, " +
                "harga DOUBLE, " +
                "subtotal DOUBLE, " +
                "FOREIGN KEY(kodetr) REFERENCES Transaksi(kodetr), " +
                "FOREIGN KEY(kode) REFERENCES Items(kode));";
        db.execSQL(createTableDetail);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Items");
        db.execSQL("DROP TABLE IF EXISTS Beli");
        db.execSQL("DROP TABLE IF EXISTS Transaksi");
        db.execSQL("DROP TABLE IF EXISTS DetailTransaksi");
        onCreate(db);
    }

    //crud jual
    public boolean insertData(String kode, String nama, String satuan, Double harga, String uriImhPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", kode);
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        cv.put("image_path", uriImhPath);
        long result = db.insert("Items", null, cv);
        return result != -1;


    }

    public ArrayList<Item_jual> readData() {
        ArrayList<Item_jual> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Items", null);
        if (cursor.moveToFirst()) {
            do {
                String kode = cursor.getString(cursor.getColumnIndexOrThrow("kode"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String satuan = cursor.getString(cursor.getColumnIndexOrThrow("satuan"));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow("harga"));
                String imgPath = cursor.getString(cursor.getColumnIndexOrThrow("image_path"));
                Item_jual itemx = new Item_jual(kode, nama, satuan, harga, imgPath);
                itemList.add(itemx);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public boolean updateData(String kode, String nama, String satuan, Double harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        int result = db.update("Items", cv, "kode = ?", new String[]{kode});
        return result > 0;
    }

    public boolean deleteData(String kode) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Items", "kode = ?", new String[]{kode});
        return result > 0;
    }

    //crud beli
    public boolean insertDataBeli(String kode, String nama, String satuan, Double harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kode", kode);
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        return db.insert("Beli", null, cv) != -1;
    }

    public ArrayList<item_beli> readDatabeli() {
        ArrayList<item_beli> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Beli", null);
        if (cursor.moveToFirst()) {
            do {
                String kode = cursor.getString(cursor.getColumnIndexOrThrow("kode"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String satuan = cursor.getString(cursor.getColumnIndexOrThrow("satuan"));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow("harga"));
                itemList.add(new item_beli(kode, nama, satuan, harga));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemList;
    }

    public boolean updateDatabeli(String kode, String nama, String satuan, Double harga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("satuan", satuan);
        cv.put("harga", harga);
        int result = db.update("Beli", cv, "kode = ?", new String[]{kode});
        return result > 0;
    }


    public boolean deleteDatabeli(String kode) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("Items", "kode = ?", new String[]{kode});
        return result > 0;
    }


    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM items";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String kode = cursor.getString(cursor.getColumnIndexOrThrow("kode"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String satuan = cursor.getString(cursor.getColumnIndexOrThrow("satuan"));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow("harga"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image_path"));
                itemList.add(new Item(kode, nama, satuan, harga, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public List<Transaksi> getAllTransaksi() {
        List<Transaksi> transaksiList = new ArrayList<>();
        String selectQuery = "SELECT * FROM transaksi ORDER BY tanggal DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String kodetr = cursor.getString(cursor.getColumnIndexOrThrow("kodetr"));
                String tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
                double grandtotal = cursor.getDouble(cursor.getColumnIndexOrThrow("grandtotal"));
                transaksiList.add(new Transaksi(kodetr, tanggal, grandtotal));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transaksiList;
    }

    // Tambahkan metode ini ke dalam class DBHelper.java
    public List<DetailTransaksi> getDetailTransaksi(String kodetr) {
        List<DetailTransaksi> detailList = new ArrayList<>();
        String selectQuery = "SELECT d.*, i.nama, i.satuan " +
                "FROM DetailTransaksi d " +
                "JOIN Items i ON d.kode = i.kode " +
                "WHERE d.kodetr = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{kodetr});

        if (cursor.moveToFirst()) {
            do {
                String kode = cursor.getString(cursor.getColumnIndexOrThrow("kode"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                String satuan = cursor.getString(cursor.getColumnIndexOrThrow("satuan"));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow("qty"));
                double harga = cursor.getDouble(cursor.getColumnIndexOrThrow("harga"));
                double subtotal = cursor.getDouble(cursor.getColumnIndexOrThrow("subtotal"));

                // Pastikan constructor DetailTransaksi(String, String, int, double, double) ada
                DetailTransaksi detail = new DetailTransaksi(kodetr, kode, nama, satuan, qty, harga, subtotal);
                detailList.add(detail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return detailList;
    }

    public boolean insertTransaksi(String kodetr, String tanggal, double grandtotal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kodetr", kodetr);
        cv.put("tanggal", tanggal);
        cv.put("grandtotal", grandtotal);
        long result = db.insert("Transaksi", null, cv);
        db.close();
        return result != -1;
    }

    public boolean insertDetailTransaksi(String kodetr, String kode, int qty, double harga, double subtotal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kodetr", kodetr);
        cv.put("kode", kode);
        cv.put("qty", qty);
        cv.put("harga", harga);
        cv.put("subtotal", subtotal);
        long result = db.insert("DetailTransaksi", null, cv);
        db.close();
        return result != -1;
    }

}