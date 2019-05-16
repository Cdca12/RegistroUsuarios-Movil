package com.example.registrousuarios_movil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registrousuarios_movil.models.Usuario;
import com.example.registrousuarios_movil.utils.Lista;
import com.example.registrousuarios_movil.utils.Nodo;


public class Consulta extends AppCompatActivity {

    private Lista<Usuario> listaUsuarios;
    private TextView txtCriterioOrdenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        listaUsuarios = (Lista<Usuario>) getIntent().getSerializableExtra("listaUsuarios");
        txtCriterioOrdenamiento = (TextView) findViewById(R.id.txtCriterioOrdenamiento);
        txtCriterioOrdenamiento.setText(txtCriterioOrdenamiento.getText() + " " + getIntent().getStringExtra("CriterioOrdenamiento"));
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
        for (Nodo<Usuario> usuarioAux = listaUsuarios.getFrente(); usuarioAux != null; usuarioAux = usuarioAux.getSig()) {
            row = new TableRow(this);

            txtNombre = new TextView(this);
            txtNombre.setText(usuarioAux.Info.getNombre());
            txtNombre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtNombre.setLayoutParams(params);
            txtNombre.setGravity(Gravity.CENTER);
            row.addView(txtNombre);

            txtEdad = new TextView(this);
            txtEdad.setText(Integer.toString(usuarioAux.Info.getEdad()));
            txtEdad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtEdad.setLayoutParams(params);
            txtEdad.setGravity(Gravity.CENTER);
            row.addView(txtEdad);

            txtEstatura = new TextView(this);
            txtEstatura.setText(Double.toString(usuarioAux.Info.getEstatura()));
            txtEstatura.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            txtEstatura.setLayoutParams(params);
            txtEstatura.setGravity(Gravity.CENTER);
            row.addView(txtEstatura);

            table.addView(row);
        }

    }
}
