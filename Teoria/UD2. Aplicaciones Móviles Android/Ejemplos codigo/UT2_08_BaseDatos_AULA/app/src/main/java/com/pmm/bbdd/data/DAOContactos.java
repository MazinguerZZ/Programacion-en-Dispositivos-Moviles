package com.pmm.bbdd.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DAOContactos extends DBAyudante {

    private Context contexto;

    public DAOContactos(@Nullable Context context) {
        super(context);
        this.contexto= contexto;
    }

    public long insertarContacto(String nombre, String telefono, String correo) {

        long id= 0;

        SQLiteDatabase db= getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("correo_electronico", correo);
        id= db.insert(DBAyudante.DATABASE_TABLA, null, values);

        return id;

    }

    public ArrayList<Contactos> mostrarContactos() {
        ArrayList<Contactos> listaContactos= new ArrayList<>();

        SQLiteDatabase db= getReadableDatabase();
        Contactos contacto= null;
        Cursor cursorContactos= db.rawQuery("SELECT * FROM " + DATABASE_TABLA, null);
        if(cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electronico(cursorContactos.getString(3));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();

        return listaContactos;
    }
}
