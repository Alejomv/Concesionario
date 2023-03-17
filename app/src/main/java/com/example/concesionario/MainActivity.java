package com.example.concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Clientes(View view){
        Intent intclientes=new Intent(this,ClienteActivity.class);
        startActivity(intclientes);
    }
    public void Vehiculos(View view){
        Intent intvehiculo1=new Intent(this, Vehiculo_Activity.class);
        startActivity(intvehiculo1);
    }
    public void Ventas (View view){
        Intent intventa=new Intent(this,VentaActivity.class);
        startActivity(intventa);
    }
}