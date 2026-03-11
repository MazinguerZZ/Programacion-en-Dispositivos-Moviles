package com.pmm.permisos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        int permisoSMS= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permisoSMS == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso SMS concedido", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permiso SMS NO concedido", Toast.LENGTH_SHORT).show();
        }

        int permisoBiometricos= ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC);
        if(permisoBiometricos == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso Biometría concedido", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permiso Biometría NO concedido", Toast.LENGTH_SHORT).show();
        }

        int permisoMedia= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_MEDIA_LOCATION);
        if(permisoMedia == PackageManager.PERMISSION_DENIED) {
            int CODIGO_REPUESTA= 200;
            Toast.makeText(this, "Permiso acceso a medios NO concedido", Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION}, CODIGO_REPUESTA);
        }


    }
}