package com.example.registrousuarios_movil;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.registrousuarios_movil.database.BaseDeDatos;
import com.example.registrousuarios_movil.models.Usuario;
import com.example.registrousuarios_movil.utils.Lista;
import com.example.registrousuarios_movil.utils.Nodo;
import com.example.registrousuarios_movil.utils.Ordenamiento;
import com.example.registrousuarios_movil.utils.UtilsCriterioOrdenamiento;

public class MainActivity extends AppCompatActivity {

    private Button btnAñadir, btnConsultar, btnLimpiar;
    private RadioButton rdBtnNombre, rdBtnEdad, rdBtnEstatura, rdBtnEdadEstNombre;
    private EditText editTextNombre, editTextEdad, editTextEstatura;
    private String query;

    // private Lista<Usuario> listaUsuarios;
    private int criterioOrdenamiento;

    // Conexión a Base de Datos
    BaseDeDatos conexion;
    SQLiteDatabase bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listaUsuarios = new Lista<>();
        criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_NOMBRE; // Default;
        bindComponents();
        addListeners();
        if (!conectarBaseDeDatos()) {
            return;
        }

    }

    private void bindComponents() {
        // Asignación del XML a Java
        btnAñadir = (Button) findViewById(R.id.btnAñadir);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        rdBtnNombre = (RadioButton) findViewById(R.id.rdBtnNombre);
        rdBtnEdad = (RadioButton) findViewById(R.id.rdBtnEdad);
        rdBtnEstatura = (RadioButton) findViewById(R.id.rdBtnEstatura);
        rdBtnEdadEstNombre = (RadioButton) findViewById(R.id.rdBtnEdadEstNombre);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextEdad = (EditText) findViewById(R.id.editTextEdad);
        editTextEstatura = (EditText) findViewById(R.id.editTextEstatura);
    }

    private void addListeners() {
        btnAñadir.setOnClickListener(view -> {
            // Abrir conexión a Base de Datos
            if (!abrirConexionBaseDeDatos()) {
                return;
            }
            query = "INSERT INTO USUARIOS (usuario_nombre, usuario_edad, usuario_estatura)" +
                    "VALUES ('" + editTextNombre.getText().toString() + "', "
                    + editTextEdad.getText().toString() + ", "
                    + editTextEstatura.getText().toString() + ");";

            // listaUsuarios.InsertaOrdenado(new Usuario(editTextNombre.getText().toString(), Integer.parseInt(editTextEdad.getText().toString()), Double.parseDouble(editTextEstatura.getText().toString()), criterioOrdenamiento));
            limpiarCampos();
            bd.execSQL(query);
            bd.close(); // Cerrar conexión a Base de Datos por seguridad
            Toast toast = Toast.makeText(this, "El usuario se ha añadido correctamente", Toast.LENGTH_SHORT);
            toast.show();
        });
        btnConsultar.setOnClickListener(view -> {
            // mostrarResultados()
            mostrarResultadosBaseDeDatos();
            limpiarCampos();
        });
        btnLimpiar.setOnClickListener(view -> {
            limpiarCampos();
        });
        rdBtnNombre.setOnClickListener(view -> {
            cambiarCriterioOrdenamiento(view);
        });
        rdBtnEdad.setOnClickListener(view -> {
            cambiarCriterioOrdenamiento(view);
        });
        rdBtnEstatura.setOnClickListener(view -> {
            cambiarCriterioOrdenamiento(view);
        });
        rdBtnEdadEstNombre.setOnClickListener(view -> {
            cambiarCriterioOrdenamiento(view);
        });

    }

    private void mostrarResultadosBaseDeDatos() {
        if(!abrirConexionBaseDeDatos()) {
            return;
        }
        String datos = "Nombre\t\t\t\t\t\t\t\tEdad\t\t\t\t\t\t\t\tEstatura\n-------------------------------------------------------------\n",
                query = "SELECT * FROM USUARIOS;";
        Cursor cursor = bd.rawQuery(query, null);
        // bd.close();
        if (cursor.getCount() == 0) {
            Toast toast = Toast.makeText(this, "No hay datos por mostrar", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        cursor.moveToFirst(); // Posiciona cursor al principio
        do {
            datos += // cursor.getString(0) + "\t" // ID
                    cursor.getString(1) + "\t\t\t\t\t\t\t\t\t\t\t " // Nombre
                    +cursor.getString(2) + "\t\t\t\t\t\t\t\t\t\t\t" // Edad
                    +cursor.getString(3) + "\n"; // Estatura
        }
        while (cursor.moveToNext());
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Datos");
        alertDialog.setMessage(datos);
        alertDialog.show();
    }

    private void limpiarCampos() {
        editTextNombre.setText(null);
        editTextEdad.setText(null);
        editTextEstatura.setText(null);
        editTextNombre.requestFocus();
    }

    private void mostrarResultados() {
        Intent intent = new Intent(this, Consulta.class);
        // intent.putExtra("listaUsuarios", listaUsuarios);
        String criterio = "";
        switch (criterioOrdenamiento) {
            case UtilsCriterioOrdenamiento.POR_NOMBRE:
                criterio = "Nombre";
                break;
            case UtilsCriterioOrdenamiento.POR_EDAD:
                criterio = "Edad";
                break;
            case UtilsCriterioOrdenamiento.POR_ESTATURA:
                criterio = "Estatura";
                break;
            case UtilsCriterioOrdenamiento.POR_EDAD_ESTATURA_NOMBRE:
                criterio = "Edad-Estatura-Nombre";
                break;
        }
        intent.putExtra("CriterioOrdenamiento", criterio);
        startActivity(intent);
    }

    private void cambiarCriterioOrdenamiento(View view) {
        String mensajeToast = "Ordenado por ";
        Toast toast;
        ; // Default
        while (true) {
            if (view == rdBtnNombre) {
                mensajeToast += "Nombre";
                criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_NOMBRE;
                break;
            }
            if (view == rdBtnEdad) {
                mensajeToast += "Edad";
                criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_EDAD;
                break;
            }
            if (view == rdBtnEstatura) {
                mensajeToast += "Estatura";
                criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_ESTATURA;
                break;
            }
            if (view == rdBtnEdadEstNombre) {
                mensajeToast += "Edad-Estatura-Nombre";
                criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_EDAD_ESTATURA_NOMBRE;
                break;
            }
        }
        // Ordenamiento.ordenamientoIntercambio(listaUsuarios, criterioOrdenamiento);
        toast = Toast.makeText(this, mensajeToast, Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean conectarBaseDeDatos() {
        conexion = new BaseDeDatos(this, "BDUSUARIOS", null, conexion.version);
        if(conexion == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("No fue posible conectarse a la base de datos");
            alertDialog.show();
            return false;
        }
        // bd = conexion.getWritableDatabase();
        Toast toast = Toast.makeText(this, "Se ha conectado a la base de datos exitosamente", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    public boolean abrirConexionBaseDeDatos() {
        bd = conexion.getWritableDatabase();
        if(bd == null){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error Permisos");
            alertDialog.setMessage("La base de datos no está abierta para escritura");
            alertDialog.show();
            return false;
        }
        return true;
    }
}
