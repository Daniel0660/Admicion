package me.parzibyte.agenda;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import me.parzibyte.agenda.modelos.Persona;

public class ActivityListar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me.parzibyte.agenda.databinding.ActivityListarBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_listar);

        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityListar.this, Utilerias.NOMBRE_BD, null, 1);
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
            binding.tvSinContactos.setVisibility(View.VISIBLE);
            return;
        }

        ArrayList<Persona> personas = new ArrayList<>();


        do {
            String nombre = cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_NOMBRE));
            String telefono = cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_TELEFONO));
            int id = cursor.getInt(cursor.getColumnIndex(Utilerias.CAMPO_ID));
            Persona persona = new Persona(nombre, telefono, id);
            personas.add(persona);
        } while (cursor.moveToNext());

        cursor.close();

        AdaptadorPersonas adaptadorPersonas = new AdaptadorPersonas(personas);
        binding.recyclerViewPersonas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerViewPersonas.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewPersonas.setAdapter(adaptadorPersonas);

    }
}
