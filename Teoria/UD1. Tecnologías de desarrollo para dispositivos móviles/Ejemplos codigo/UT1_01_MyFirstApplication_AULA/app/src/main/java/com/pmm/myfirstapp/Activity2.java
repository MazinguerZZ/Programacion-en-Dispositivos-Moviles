package com.pmm.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras= getIntent().getExtras();
        int valor= extras.getInt("MiValor");
        String texto= extras.getString("MiValor2");
        Toast.makeText(this, "El valor recibido es: "+texto, Toast.LENGTH_LONG).show();
    }

    public void crearAlarma(View view) {
        Intent intent= new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "Ir al trabajo")
                .putExtra(AlarmClock.EXTRA_HOUR, 12)
                .putExtra(AlarmClock.EXTRA_MINUTES, 15);
        startActivity(intent);
    }

    public void llamarTelefono(View view) {
        Intent intent= new Intent(Intent.ACTION_DIAL)
                .setData(Uri.parse("tel:"+112));
        startActivity(intent);
    }
}