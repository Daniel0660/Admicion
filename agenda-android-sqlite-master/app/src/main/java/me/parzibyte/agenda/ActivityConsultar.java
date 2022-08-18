package me.parzibyte.agenda;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import me.parzibyte.agenda.databinding.ActivityConsultarBinding;

public class ActivityConsultar extends AppCompatActivity {
    private ActivityConsultarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_consultar);

        binding.btnBuscar.setOnClickListener(v -> {
            String idBusqueda = binding.etIdBusqueda.getText().toString();
            if (idBusqueda.isEmpty()) return;
            ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityConsultar.this, Utilerias.NOMBRE_BD, null, 1);
            SQLiteDatabase bd = conexion.getReadableDatabase();
            String[] parametros = {idBusqueda};
            String[] campos = {Utilerias.CAMPO_NOMBRE, Utilerias.CAMPO_TELEFONO};
            @SuppressLint("Recycle") Cursor cursor = bd.query(Utilerias.TABLA_PERSONA,
                    campos,
                    Utilerias.CAMPO_ID + "=?",
                    parametros,
                    null, null, Utilerias.CAMPO_ID);
            if (!cursor.moveToFirst()) {
                Toast.makeText(ActivityConsultar.this, "ID Inexistente", Toast.LENGTH_SHORT).show();
                binding.etMostrarNombre.setText("");
                binding.etMostrarTelefono.setText("");
                bd.close();
                return;
            }

            binding.etMostrarNombre.setText(cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_NOMBRE)));
            binding.etMostrarTelefono.setText(cursor.getString(cursor.getColumnIndex(Utilerias.CAMPO_TELEFONO)));

        });
    }
}
