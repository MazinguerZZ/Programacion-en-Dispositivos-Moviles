package com.pmm.bbdd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAyudante extends SQLiteOpenHelper {

    private static final String DATABASE_NOMBRE= "agenda.db";
    private static final int DATABASE_VERSION= 1;
    protected static final String DATABASE_TABLA= "t_contactos";

    public DBAyudante(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery= "CREATE TABLE " + DATABASE_TABLA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "correo_electronico TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DATABASE_TABLA);
        onCreate(db);
    }
}
