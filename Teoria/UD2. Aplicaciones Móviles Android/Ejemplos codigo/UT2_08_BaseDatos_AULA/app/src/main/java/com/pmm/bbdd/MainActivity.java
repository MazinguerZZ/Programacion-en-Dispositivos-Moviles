package com.pmm.bbdd;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pmm.bbdd.UI.ListaContactosAdapter;
import com.pmm.bbdd.data.Contactos;
import com.pmm.bbdd.data.DAOContactos;
import com.pmm.bbdd.data.DBAyudante;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listaContactos;
    private EditText txtNombre;
    private EditText txtTelefono;

    private EditText txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnBaseDatos= findViewById(R.id.btnCreacion);
        btnBaseDatos.setOnClickListener(v->{
            try(DBAyudante dbAyudante= new DBAyudante(MainActivity.this)) {
                SQLiteDatabase db= dbAyudante.getWritableDatabase();
                if (db != null) {
                    Toast.makeText(MainActivity.this, "Base de datos creada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al crear la base de datos", Toast.LENGTH_LONG).show();
                }

            }
        });

        txtNombre= findViewById(R.id.txtNombre);
        txtTelefono= findViewById(R.id.txtTelefono);
        txtCorreo= findViewById(R.id.txtCorreo);

        Button btnInsertar= findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(v->{
            try(DAOContactos DAOcontactos= new DAOContactos(MainActivity.this)) {
               long id= DAOcontactos.insertarContacto(
                        txtNombre.getText().toString(),
                        txtTelefono.getText().toString(),
                        txtCorreo.getText().toString()
                );
               if(id>0) {
                   Toast.makeText(this, "El registro ha sido insertado", Toast.LENGTH_SHORT).show();
                   monstrarContactos();
                   limpiar();
               } else {
                   Toast.makeText(this, "El registro NO ha sido insertado", Toast.LENGTH_SHORT).show();
               }
            }


        });

        listaContactos= findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));
        monstrarContactos();
    }

    private void limpiar() {
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

    private void monstrarContactos() {
        try(DAOContactos DAOcontactos= new DAOContactos(MainActivity.this)) {
            ListaContactosAdapter adapter= new ListaContactosAdapter(DAOcontactos.mostrarContactos());
            listaContactos.setAdapter(adapter);
        }
    }
}