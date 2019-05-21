package com.example.registrousuarios_movil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registrousuarios_movil.database.BaseDeDatos;
import com.example.registrousuarios_movil.utils.UtilsCriterioOrdenamiento;


public class Consulta extends AppCompatActivity {

    private String query = "SELECT * FROM USUARIOS ORDER BY ";
    private TextView txtCriterioOrdenamiento;
    BaseDeDatos conexion;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        // Conectar a la base de datos
        if (!conectarBaseDeDatos()) {
            return;
        }

        iniComponents();
    }

    private void iniComponents() {
        String criterio = "";
        switch (getIntent().getIntExtra("CriterioOrdenamiento", UtilsCriterioOrdenamiento.POR_NOMBRE)) {
            case UtilsCriterioOrdenamiento.POR_NOMBRE:
                criterio = "Nombre";
                query += "usuario_nombre ASC";
                break;
            case UtilsCriterioOrdenamiento.POR_EDAD:
                criterio = "Edad";
                query += "usuario_edad ASC";
                break;
            case UtilsCriterioOrdenamiento.POR_ESTATURA:
                criterio = "Estatura";
                query += "usuario_estatura ASC";
                break;
            case UtilsCriterioOrdenamiento.POR_EDAD_ESTATURA_NOMBRE:
                criterio = "Edad-Estatura-Nombre";
                query += "usuario_edad ASC, usuario_estatura ASC, usuario_nombre ASC";
                break;
        }
        txtCriterioOrdenamiento = (TextView) findViewById(R.id.txtCriterioOrdenamiento);
        txtCriterioOrdenamiento.setText(txtCriterioOrdenamiento.getText() + " " + criterio);
        rellenarTabla();
    }

    private void rellenarTabla() {
        TableLayout table = (TableLayout) findViewById(R.id.tbTable);

        TableRow row;
        TextView txtNombre, txtEdad, txtEstatura;
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f
        );

        // Abrir conexión a Base de Datos
        if (!abrirConexionBaseDeDatos()) {
            return;
        }
        Cursor cursor = bd.rawQuery(query, null);
        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No hay datos por mostrar", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        cursor.moveToFirst(); // Posiciona cursor al principio
        do {
            row = new TableRow(this);

            txtNombre = new TextView(this);
            txtNombre.setText(cursor.getString(1));
            txtNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtNombre.setLayoutParams(params);
            txtNombre.setGravity(Gravity.CENTER);
            row.addView(txtNombre);

            txtEdad = new TextView(this);
            txtEdad.setText(cursor.getString(2));
            txtEdad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtEdad.setLayoutParams(params);
            txtEdad.setGravity(Gravity.CENTER);
            row.addView(txtEdad);

            txtEstatura = new TextView(this);
            txtEstatura.setText(cursor.getString(3));
            txtEstatura.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtEstatura.setLayoutParams(params);
            txtEstatura.setGravity(Gravity.CENTER);
            row.addView(txtEstatura);

            table.addView(row);
        } while (cursor.moveToNext());
        bd.close(); // Se cierra la conexión con la bd
    }

    public boolean conectarBaseDeDatos() {
        conexion = conexion.getInstance(this);
        if (conexion == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("No fue posible conectarse a la base de datos");
            alertDialog.show();
            return false;
        }
        // Toast toast = Toast.makeText(this, "Se ha conectado a la base de datos exitosamente", Toast.LENGTH_SHORT);
        // toast.show();
        return true;
    }

    public boolean abrirConexionBaseDeDatos() {
        bd = conexion.getWritableDatabase();
        if (bd == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error Permisos");
            alertDialog.setMessage("La base de datos no está abierta para escritura");
            alertDialog.show();
            return false;
        }
        return true;
    }

}
