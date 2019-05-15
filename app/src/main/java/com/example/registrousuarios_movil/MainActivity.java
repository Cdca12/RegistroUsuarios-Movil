package com.example.registrousuarios_movil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.registrousuarios_movil.models.Usuario;
import com.example.registrousuarios_movil.utils.Lista;
import com.example.registrousuarios_movil.utils.Nodo;
import com.example.registrousuarios_movil.utils.UtilsCriterioOrdenamiento;

public class MainActivity extends AppCompatActivity {

    private Button btnAñadir, btnConsultar, btnLimpiar;
    private RadioButton rdBtnNombre, rdBtnEdad, rdBtnEstatura, rdBtnEdadEstNombre;
    private EditText editTextNombre, editTextEdad, editTextEstatura;

    private Lista<Usuario> listaUsuarios;
    private int criterioOrdenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaUsuarios = new Lista<>();
        criterioOrdenamiento = UtilsCriterioOrdenamiento.POR_NOMBRE; // Default;
        bindComponents();
        addListeners();
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
            listaUsuarios.InsertaOrdenado(new Usuario(editTextNombre.getText().toString(), Integer.parseInt(editTextEdad.getText().toString()), Double.parseDouble(editTextEstatura.getText().toString()), criterioOrdenamiento));
            limpiarCampos();
        });
        btnConsultar.setOnClickListener(view -> {
            Toast mensajeToast;
            Nodo<Usuario> usuarioAux = listaUsuarios.getFrente();
            int i = 0;
            String cadena;
            while (usuarioAux != null) {
                // TODO: mostrar otra Activity o un modal con los resultados
                mensajeToast = Toast.makeText(this, usuarioAux.Info.mostrarInformacion(), Toast.LENGTH_SHORT);
                // cadena = "Usuario " + (i + 1);
                // mensajeToast = Toast.makeText(this, cadena, Toast.LENGTH_SHORT);
                mensajeToast.show();
                i++;
                usuarioAux = usuarioAux.getSig();
            }
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

    public void limpiarCampos() {
        editTextNombre.setText(null);
        editTextEdad.setText(null);
        editTextEstatura.setText(null);
        editTextNombre.requestFocus();
    }

    private void cambiarCriterioOrdenamiento(View view) {
        Toast mensajeToast = Toast.makeText(this, "Default", Toast.LENGTH_SHORT);; // Default
        if (view == rdBtnNombre) {
            mensajeToast = Toast.makeText(this, "Ordenado por Nombre", Toast.LENGTH_SHORT);
        }
        if (view == rdBtnEdad) {
            mensajeToast = Toast.makeText(this, "Ordenado por Edad", Toast.LENGTH_SHORT);
        }
        if (view == rdBtnEstatura) {
            mensajeToast = Toast.makeText(this, "Ordenado por Estatura", Toast.LENGTH_SHORT);
        }
        if (view == rdBtnEdadEstNombre) {
            mensajeToast = Toast.makeText(this, "Ordenado por Edad-Estatura-Nombre", Toast.LENGTH_SHORT);
        }
        mensajeToast.show();

    }









}
