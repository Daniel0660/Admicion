package me.parzibyte.agenda;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import me.parzibyte.agenda.databinding.ActivityRegistrarBinding;

public class ActivityRegistrar extends AppCompatActivity {
    private ActivityRegistrarBinding binding;

    private boolean existe(String id) {
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityRegistrar.this, Utilerias.NOMBRE_BD, null, 1);
        SQLiteDatabase bd = conexion.getReadableDatabase();
        String[] parametros = {id};
        String[] campos = {Utilerias.CAMPO_NOMBRE};
        @SuppressLint("Recycle") Cursor cursor = bd.query(Utilerias.TABLA_PERSONA,
                campos,
                Utilerias.CAMPO_ID + "=?",
                parametros,
                null,
                null,
                null);
        return cursor.moveToFirst();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registrar);
        binding.etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarBoton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etIdentificador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarBoton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.etTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validarBoton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.btnRegistrar.setOnClickListener(v -> {
            String identificador = binding.etIdentificador.getText().toString(),
                    nombre = binding.etNombre.getText().toString(),
                    telefono = binding.etTelefono.getText().toString();
            if (existe(identificador)) {
                Toast.makeText(ActivityRegistrar.this, "Identificador existente", Toast.LENGTH_SHORT).show();
                return;
            }

            if (identificador.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) return;
            ContentValues contentValues = new ContentValues();
            contentValues.put(Utilerias.CAMPO_ID, identificador);
            contentValues.put(Utilerias.CAMPO_NOMBRE, nombre);
            contentValues.put(Utilerias.CAMPO_TELEFONO, telefono);

            ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(ActivityRegistrar.this,
                    Utilerias.NOMBRE_BD, null, 1);
            SQLiteDatabase bd = conexion.getWritableDatabase();
            long respuesta = bd.insert(Utilerias.TABLA_PERSONA, null, contentValues);
            bd.close();
            Toast.makeText(ActivityRegistrar.this, "Registro #" + respuesta, Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }

    private void validarBoton() {
        if (binding.etNombre.getText().toString().isEmpty()){
            binding.btnRegistrar.setAlpha(0.5f);
            binding.btnRegistrar.setEnabled(false);
            return;
        }
        if (binding.etIdentificador.getText().toString().isEmpty()) {
            binding.btnRegistrar.setAlpha(0.5f);
            binding.btnRegistrar.setEnabled(false);
            return;
        }
        if (binding.etTelefono.getText().toString().isEmpty() || binding.etTelefono.getText().toString().length() < 10) {
            binding.btnRegistrar.setAlpha(0.5f);
            binding.btnRegistrar.setEnabled(false);
            return;
        }
        binding.btnRegistrar.setAlpha(1f);
        binding.btnRegistrar.setEnabled(true);
    }
}
