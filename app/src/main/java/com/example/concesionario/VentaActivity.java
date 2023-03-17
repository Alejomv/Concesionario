package com.example.concesionario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VentaActivity extends AppCompatActivity {

    EditText jetcodigo, jetidentificacion,jetplaca,jfecha,jnombre,jplaca,jmodelo,jmarca;
    CheckBox jactivo;
    String codigo, placa, identificacion, marca, modelo, fecha,nombre;
    ClsOpenHelper Admin = new ClsOpenHelper(this, "Consecionario.db", null, 1);
    long respuesta,respuesta2;;
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        jetidentificacion = findViewById(R.id.etidentificacion);
        jetcodigo = findViewById(R.id.etcodigo);
        jetplaca = findViewById(R.id.etplaca2);
        jfecha = findViewById(R.id.etfecha);
        jnombre = findViewById(R.id.etnombre);
        jmodelo = findViewById(R.id.etmodelo2);
        jmarca = findViewById(R.id.etmarca2);
        sw=0;

    }

    public void Regresar (View view){
        Intent MainActivity=new Intent(this,MainActivity.class);
        startActivity(MainActivity);
    }

    private void limpiarcampos() {
        jetidentificacion.setText("");
        jetcodigo.setText("");
        jetplaca.setText("");
        jactivo.setChecked(false);
        jmarca.setText("");
        jetplaca.setText("");
        jetplaca.setText("");
        jetidentificacion.requestFocus();



    }


    public void Guardar(View view) {
        identificacion = jetidentificacion.getText().toString();
        placa = jetplaca.getText().toString();
        codigo = jetcodigo.getText().toString();
        fecha = jfecha.getText().toString();

        if (identificacion.isEmpty() || placa.isEmpty() || codigo.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetidentificacion.requestFocus();
        } else {
            SQLiteDatabase db = Admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("identificacion", identificacion);
            registro.put("placa", placa);
            registro.put("codigo", codigo);
            registro.put("fecha", fecha);
            sw = 1;
            respuesta = db.insert("TblVenta", null, registro);
            ContentValues registroa = new ContentValues();
            registroa.put("activo", "No");
            respuesta2 = db.update("TblVehiculo", registroa, "placa='" + placa + "'", null);

            Toast.makeText(this, "Vehiculo vendido", Toast.LENGTH_SHORT).show();
            db.close();
            Toast.makeText(this, "Facturado", Toast.LENGTH_SHORT).show();
        }
    }

    public void Consultar(View view) {

        identificacion = jetidentificacion.getText().toString();
        placa = jetplaca.getText().toString();

         SQLiteDatabase db = Admin.getWritableDatabase();
        if (!identificacion.isEmpty()) {



            Cursor fila = db.rawQuery("select * from TblCliente where identificacion='" + identificacion + "'", null);

            if (fila.moveToNext()) {
                sw = 1;
                if (fila.getString(3).equals("Si")) {
                    //Consulta Cliente
                    jnombre.setText(fila.getString(1));

                    jetidentificacion.setEnabled(false);
                    jnombre.setEnabled(false);


                } else {
                    Toast.makeText(this, "Cliente inactivo", Toast.LENGTH_SHORT).show();
                    jetidentificacion.setText("");
                }
            } else {
                Toast.makeText(this, "Cliente no eciste ", Toast.LENGTH_SHORT).show();


                db.close();
            }
        } else {
            Toast.makeText(this, "Digite CC  ", Toast.LENGTH_SHORT).show();

        }


        if (!placa.isEmpty()) {

            Cursor filab = db.rawQuery("select * from TblVehiculo where placa='" + placa + "'", null);

            if (filab.moveToNext()) {
                sw = 1;


                if (filab.getString(3).equals("Si")) {

                    //Consulta carro
                    jmodelo.setText(filab.getString(1));
                    jmarca.setText(filab.getString(2));

                    jmodelo.setEnabled(false);
                    jmarca.setEnabled(false);
                } else {
                    Toast.makeText(this, "Vehiculo inactivo", Toast.LENGTH_SHORT).show();
                    jetidentificacion.setText("");
                }
            } else {
                Toast.makeText(this, "Vehiculo invalida ", Toast.LENGTH_SHORT).show();

                //Cierra base de datos

            }
        } else {
            Toast.makeText(this, "Digite la Placa ", Toast.LENGTH_SHORT).show();

        }
        db.close();
        sw = 0;
    }









}
