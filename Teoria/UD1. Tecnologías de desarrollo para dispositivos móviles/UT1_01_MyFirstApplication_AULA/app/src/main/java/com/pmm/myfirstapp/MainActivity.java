package com.pmm.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int i;

    private TextView txtMessage;

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

        txtMessage= (TextView) findViewById(R.id.txtMessage);
        txtMessage.setText("Hola Mundo");
        Log.i("TAG Ciclo de vida", "CicloVida: OnCreate");


        if(savedInstanceState == null) {
            i=0;
        }
        else{
            i= (savedInstanceState.getInt("value"));
        }


        /**
        txtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Has hecho click en el texto 2", Toast.LENGTH_SHORT).show();
            }
        });
    */
        txtMessage.setOnClickListener(this::onClickTextView);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("TAG Ciclo de vida", "CicloVida: OnStart");

        i++;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG Ciclo de vida", "CicloVida: OnResume");

        Toast.makeText(this, "App activa de nuevo", Toast.LENGTH_LONG).show();

        i++;
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG Ciclo de vida", "CicloVida: OnPause");

        i++;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG Ciclo de vida", "CicloVida: OnStop");

        i--;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG Ciclo de vida", "CicloVida: OnDestroy");

        i--;
    }

    public void onClickTextView(View view) {
        Toast.makeText(this, "Has hecho click en el texto", Toast.LENGTH_SHORT).show();
    }

    public void iniciarActivity2(View view) {
        Intent intentActivity= new Intent(this, Activity2.class);
        intentActivity.putExtra("MiValor", 2222);
        intentActivity.putExtra("MiValor2", "TEXTO");
        startActivity(intentActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("value", i);
    }
}