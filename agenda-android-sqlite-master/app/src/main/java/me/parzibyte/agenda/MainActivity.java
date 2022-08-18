package me.parzibyte.agenda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import me.parzibyte.agenda.databinding.ActivityMainBinding;
import me.parzibyte.agenda.modelos.Persona;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Persona> personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.cvRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityRegistrar.class);
            startActivity(intent);
        });
        binding.cvBuscar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityConsultar.class);
            startActivity(intent);
        });
        binding.cvListar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActivityListar.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        personas = new ArrayList<>();
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(MainActivity.this, Utilerias.NOMBRE_BD, null, 1);
        SQLiteDatabase bd = conexion.getReadableDatabase();
        String[] campos = {Utilerias.CAMPO_NOMBRE, Utilerias.CAMPO_TELEFONO, Utilerias.CAMPO_ID};
        Cursor cursor = bd.query(Utilerias.TABLA_PERSONA,
                campos,
                null,
                null,
                null,
                null,
                null);
        if (!cursor.moveToFirst()) {
            cursor.close();
            bd.close();
            String cadena = "En estos momentos cuentas con <strong>0</strong> Usuarios registrados";
            binding.tvDescription.setText(Html.fromHtml(cadena));
            return;
        }



        do {
            String nombre = cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_NOMBRE));
            String telefono = cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_TELEFONO));
            int id = cursor.getInt(cursor.getColumnIndex(Utilerias.CAMPO_ID));
            Persona persona = new Persona(nombre, telefono, id);
            personas.add(persona);
        } while (cursor.moveToNext());

        cursor.close();
        String cadena = "En estos momentos cuentas con <strong>"+personas.size()+"</strong> Usuarios registrados";
        binding.tvDescription.setText(Html.fromHtml(cadena));
    }
}
