package com.example.registrousuarios_movil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.registrousuarios_movil.models.Usuario;
import com.example.registrousuarios_movil.utils.Lista;
import com.example.registrousuarios_movil.utils.Nodo;


public class Consulta extends AppCompatActivity {

    private Lista<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        listaUsuarios = (Lista<Usuario>) getIntent().getSerializableExtra("listaUsuarios");

    }
}
