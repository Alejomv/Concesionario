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

public class Vehiculo_Activity extends AppCompatActivity {

    EditText jetplaca, jetmodelo,jetmarca;
    CheckBox jactivo;
    String placa, marca, modelo;
    ClsOpenHelper Admin = new ClsOpenHelper(this, "Consecionario.db", null, 1);
    long respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        jetplaca = findViewById(R.id.etplaca);
        jetmarca = findViewById(R.id.etmarca);
        jetmodelo = findViewById(R.id.etmodelo);
        jactivo = findViewById(R.id.cbactivo);


    }
    public void Guardar(View view) {

        placa = jetplaca.getText().toString();
        modelo = jetmodelo.getText().toString();
        marca = jetmarca.getText().toString();


        if (!placa.isEmpty() || !modelo.isEmpty() || !marca.isEmpty()) {
            SQLiteDatabase Search = Admin.getReadableDatabase();
            String query = "SELECT placa FROM TblVehiculo WHERE placa = '" + jetplaca.getText().toString() + "'";
            Cursor tsearch = Admin.getReadableDatabase().rawQuery(query, null);

            if (!tsearch.moveToFirst())
            {

                SQLiteDatabase db = Admin.getWritableDatabase();
                ContentValues registro = new ContentValues();
                registro.put("placa", placa);
                registro.put("modelo", modelo);
                registro.put("marca", marca);
                respuesta = db.insert("TblCliente", null, registro);
                db.close();
                limpiarcampos();
                Toast.makeText(this, "Registro Guardado", Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getApplicationContext(),
                        "Identificacion está asignada a otro Usuario", Toast.LENGTH_SHORT).show();

            }

        }else {

            Toast.makeText(getApplicationContext(),
                    "Debe ingresar todos los datos...", Toast.LENGTH_SHORT).show();




        }
    }

    public void Consultar (View view) {


        SQLiteDatabase Search = Admin.getReadableDatabase();
        String query = "SELECT placa, marca, modelo  FROM TblVehiculo WHERE placa = '" + jetplaca.getText().toString() + "'";
        Cursor tsearch = Admin.getReadableDatabase().rawQuery(query, null);
        if (tsearch.moveToFirst()) { //Encontró el idseller
            jetplaca.setText(tsearch.getString(0));
            jetmarca.setText(tsearch.getString(1));
            jetmodelo.setText(tsearch.getString(2));

        } else {
            Toast.makeText(getApplicationContext(), "La identificacion no existe ..", Toast.LENGTH_SHORT).show();
        }
    }








    private void limpiarcampos() {
        jetplaca.setText("");
        jetmarca.setText("");
        jetmodelo.setText("");
        jactivo.setChecked(false);
        jetplaca.requestFocus();

    }

    public void Regresar (View view){
        Intent MainActivity=new Intent(this,MainActivity.class);
        startActivity(MainActivity);
    }

}

