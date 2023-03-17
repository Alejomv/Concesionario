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

public class ClienteActivity extends AppCompatActivity {
// juantopo es pato y nos abandono.
    //ADEMAS ES GAY Y AMA A ALEJO(DELFIN) EN SUS NOCHES DE SOLEDAD SE TOCA PENSANDO EN EL
    //NO PUEDE VIVIR SIN EL HASTA CUESTIONA SU EXISTENCIA
    EditText jetidentificacion, jetnombre, jetcorreo;
    CheckBox jactivo;
    String identificacion, nombre, correo;
    ClsOpenHelper Admin = new ClsOpenHelper(this, "Consecionario.db", null, 1);
    long respuesta;
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        jetidentificacion = findViewById(R.id.etidentificacion);
        jetnombre = findViewById(R.id.etnombre);
        jetcorreo = findViewById(R.id.etcorreo);
        jactivo = findViewById(R.id.cbactivo);


    }

    public void Guardar(View view) {

        identificacion = jetidentificacion.getText().toString();
        nombre = jetnombre.getText().toString();
        correo = jetcorreo.getText().toString();


        //Comentario 1


        ////// Actualizacion 1


        if (!identificacion.isEmpty() || !nombre.isEmpty() || !correo.isEmpty()) {
            SQLiteDatabase Search = Admin.getReadableDatabase();
            String query = "SELECT identificacion FROM TblCliente WHERE identificacion = '" + jetidentificacion.getText().toString() + "'";
            Cursor tsearch = Admin.getReadableDatabase().rawQuery(query, null);


            if (!tsearch.moveToFirst()) {

                SQLiteDatabase db = Admin.getWritableDatabase();
                ContentValues registro = new ContentValues();
                registro.put("identificacion", identificacion);
                registro.put("nombre", nombre);
                registro.put("correo", correo);

                if (sw == 0){

                    respuesta = db.insert("TblCliente", null, registro);

               }else{
                    respuesta = db.update("TblCliente", registro,"identificacion='"+identificacion+"'",null);
                sw=0;}

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
            String query = "SELECT *  FROM TblCliente WHERE identificacion = '" + jetidentificacion.getText().toString() + "'";
            Cursor tsearch = Admin.getReadableDatabase().rawQuery(query, null);

            if (tsearch.moveToFirst()) { //Encontró el idseller
                sw=1;
                //jetidentificactsearch.getString(0));ion.setText(
                jetnombre.setText(tsearch.getString(1));
                jetcorreo.setText(tsearch.getString(2));
                if (tsearch.getString(3).equals("Si"))
                    jactivo.setChecked(true);
                else
                    jactivo.setChecked(false);

            } else {

                Toast.makeText(getApplicationContext(), "La identificacion no existe ..", Toast.LENGTH_SHORT).show();
            }
        }


    private void limpiarcampos() {
        jetidentificacion.setText("");
        jetnombre.setText("");
        jetcorreo.setText("");
        jactivo.setChecked(false);
        jetidentificacion.requestFocus();


    }

    public void Regresar (View view){
        Intent MainActivity=new Intent(this,MainActivity.class);
        startActivity(MainActivity);
    }


    public void Anular (View view){





    }
}





