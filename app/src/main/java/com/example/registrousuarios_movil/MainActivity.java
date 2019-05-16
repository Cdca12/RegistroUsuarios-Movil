package com.example.registrousuarios_movil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.registrousuarios_movil.models.Usuario;
import com.example.registrousuarios_movil.utils.Lista;
import com.example.registrousuarios_movil.utils.Nodo;
import com.example.registrousuarios_movil.utils.Ordenamiento;
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
            Toast toast = Toast.makeText(this, "El usuario se ha añadido correctamente", Toast.LENGTH_SHORT);
            toast.show();
        });
        btnConsultar.setOnClickListener(view -> {
            // mostrarResultadosToast();
            mostrarResultados();
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

    private void limpiarCampos() {
        editTextNombre.setText(null);
        editTextEdad.setText(null);
        editTextEstatura.setText(null);
        editTextNombre.requestFocus();
    }

    private void mostrarResultados() {
        Intent intent = new Intent(this, Consulta.class);
        intent.putExtra("listaUsuarios", listaUsuarios);
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
        Ordenamiento.ordenamientoIntercambio(listaUsuarios, criterioOrdenamiento);
        toast = Toast.makeText(this, mensajeToast, Toast.LENGTH_SHORT);
        toast.show();

    }

    public void mostrarResultadosToast() {
        Toast toastTest;
        Nodo<Usuario> usuarioAux = listaUsuarios.getFrente();
        int i = 0;
        String cadena;
        while (usuarioAux != null) {
            // TODO: mostrar otra Activity o un modal con los resultados
            toastTest = Toast.makeText(this, usuarioAux.Info.mostrarInformacion(), Toast.LENGTH_SHORT);
            // cadena = "Usuario " + (i + 1);
            // mensajeToast = Toast.makeText(this, cadena, Toast.LENGTH_SHORT);
            toastTest.show();
            i++;
            usuarioAux = usuarioAux.getSig();
        }
    }
}
