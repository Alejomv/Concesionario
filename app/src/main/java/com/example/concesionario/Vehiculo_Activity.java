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
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        jetplaca = findViewById(R.id.etplaca);
        jetmarca = findViewById(R.id.etmarca);
        jetmodelo = findViewById(R.id.etmodelo);
        jactivo = findViewById(R.id.cbactivo);
        sw=0;


    }

    public void Anular(View view){
        SQLiteDatabase db=Admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select * from TblVehiculo where placa='" + placa + "'", null);
        if (fila.moveToNext()) {
            sw = 1;
            if (fila.getString(3).equals("Si") ) {
                ContentValues registro = new ContentValues();
                registro.put("activo", "No");
                respuesta = db.update("TblVehiculo", registro, "placa='" + placa + "'", null);
                jactivo.setChecked(false);
                Toast.makeText(this, "Registro anulado", Toast.LENGTH_SHORT).show();
            }
            else if(fila.getString(3).equals("No") ) {
                ContentValues registro = new ContentValues();
                registro.put("activo", "Si");
                respuesta = db.update("TblVehiculo", registro, "placa='" + placa + "'", null);
                jactivo.setChecked(true);
                Toast.makeText(this, "Registro activado", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Vehiculo no hallado", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }


    public void Guardar(View view){
        placa=jetplaca.getText().toString();
        modelo=jetmodelo.getText().toString();
        marca=jetmarca.getText().toString();
        if (placa.isEmpty() || modelo.isEmpty() || marca.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }else{

            //Siempre debe validar que nos e cambie placa o el identificación
            SQLiteDatabase db=Admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("placa",placa);
            registro.put("modelo",modelo);
            registro.put("marca",marca);
            if (sw==0)
                respuesta=db.insert("TblVehiculo",null,registro);
            else{
                respuesta=db.update("TblVehiculo",registro,"placa='"+placa+"'",null);
                sw=0;
            }

            if (respuesta == 0){
                Toast.makeText(this, "Error guardando registro", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
                limpiarcampos();
            }
            db.close();
        }
    }



    public void Consultar(View view){

        placa=jetplaca.getText().toString();

        if (!placa.isEmpty()){
            SQLiteDatabase db=Admin.getReadableDatabase();
            Cursor fila=db.rawQuery("select * from TblVehiculo where placa='"+placa+"'",null);
            if (fila.moveToNext()) {
                sw = 1;
                jetmodelo.setText(fila.getString(1));
                jetmarca.setText(fila.getString(2));
                if (fila.getString(3).equals("Si")) {
                    jactivo.setChecked(true);

                } else {
                    jactivo.setChecked(false);
                }

                jetplaca.setEnabled(false);
                jetmodelo.setEnabled(false);
                jetmarca.setEnabled(false);
            }
            else{
                Toast.makeText(this, "Registro no hallado", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else{
            Toast.makeText(this, "Identificacion es requerida para consultar", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
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

