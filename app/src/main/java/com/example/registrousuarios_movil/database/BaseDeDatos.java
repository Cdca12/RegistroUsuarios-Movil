package com.example.registrousuarios_movil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {
    public static BaseDeDatos instanciaBD;
    public static int version = 1;
    String queryUsuarios = "" +
            "CREATE TABLE USUARIOS(" +
            "   usuario_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "   usuario_nombre text NOT NULL," +
            "   usuario_edad integer NOT NULL," +
            "   usuario_estatura real NOT NULL);";

    public BaseDeDatos(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    public static BaseDeDatos getInstance(Context context) {
        if(instanciaBD == null) {
            instanciaBD = new BaseDeDatos(context, "BDUSUARIOS", null, version);
        }
        return instanciaBD;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryUsuarios);
        version++;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS queryUsuarios;");
        db.execSQL(queryUsuarios);
    }

}

