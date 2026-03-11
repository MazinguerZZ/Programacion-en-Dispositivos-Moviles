package com.pmm.serviciosgps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

public class ServicioGPS extends Service implements LocationListener {

    private double latitud;
    private double longitud;
    private Context contexto;
    private LocationManager locationManager;
    private Location localizacion;
    private TextView texto;

    public ServicioGPS(Context contexto) {
        this.contexto= contexto;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void getLocation() {
        locationManager= (LocationManager) this.contexto.getSystemService(LOCATION_SERVICE);
        boolean gpsActivo= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(gpsActivo) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);
            localizacion= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitud= localizacion.getLongitude();
            latitud= localizacion.getLatitude();
        }
    }

    public void setView (View v) {
        texto= (TextView)v;
        String mensaje= "Coordenadas: " + latitud + ", " + longitud;
        texto.setText(mensaje);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}
