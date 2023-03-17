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
    long respuesta;


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

        codigo = jetcodigo.getText().toString();
        fecha = jfecha.getText().toString();
        identificacion = jetidentificacion.getText().toString();
        placa = jplaca.getText().toString();


        if (placa.isEmpty() || codigo.isEmpty() || identificacion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Todos los datos son necesarios ", Toast.LENGTH_SHORT).show();
        }else{
            {
                SQLiteDatabase db = Admin.getWritableDatabase();
                ContentValues registro = new ContentValues();
                registro.put("codigo", codigo);
                registro.put("fecha", fecha);
                registro.put("identificacion", identificacion);
                registro.put("placa", placa);


                respuesta = db.insert("TblVenta", null, registro);
                db.close();
                limpiarcampos();
                Toast.makeText(this, "Registro Guardado", Toast.LENGTH_SHORT).show();


            }

        }
    }


    public void Consultar (View view) {


        SQLiteDatabase Search = Admin.getReadableDatabase();
        String query = "SELECT codigo, TblCliente.nombre, TblCliente.identificacion, TblVehiculo.placa, TblVenta.fecha, TblVehiculo.marca, TblVehiculo.modelo from TblVenta INNER JOIN TblCliente ON TblVenta.identificacion = TblCliente.identificacion INNER JOIN TblVehiculo ON TblVenta.placa = TblVehiculo.placa  WHERE codigo= '" + jetcodigo.getText().toString() + "'";
        Cursor tsearch = Admin.getReadableDatabase().rawQuery(query, null);
        if (tsearch.moveToFirst()) { //Encontró el idseller
            jetcodigo.setText(tsearch.getString(0));
            jfecha.setText(tsearch.getString(1));
            jnombre.setText(tsearch.getString(2));
            jetidentificacion.setText(tsearch.getString(3));
            jplaca.setText(tsearch.getString(4));
            jmodelo.setText(tsearch.getString(5));
            jmarca.setText(tsearch.getString(3));


        } else {
            Toast.makeText(getApplicationContext(), "El codigo no Existe ..", Toast.LENGTH_SHORT).show();
        }
    }







}
