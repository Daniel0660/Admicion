package me.parzibyte.agenda;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.parzibyte.agenda.databinding.FilaPersonaBinding;
import me.parzibyte.agenda.modelos.Persona;

public class AdaptadorPersonas extends RecyclerView.Adapter<AdaptadorPersonas.MyViewHolder> {
    private List<Persona> listaDePersonas;

    public List<Persona> getListaDePersonas() {
        return listaDePersonas;
    }

    public void setListaDePersonas(List<Persona> listaDePersonas) {
        this.listaDePersonas = listaDePersonas;
    }

    public AdaptadorPersonas(List<Persona> listaDePersonas) {
        this.listaDePersonas = listaDePersonas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(FilaPersonaBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Persona persona = listaDePersonas.get(i);
        myViewHolder.binding.tvFilaNombre.setText(persona.getNombre());
        myViewHolder.binding.tvFilaEdad.setText(persona.getTelefono());
        myViewHolder.binding.tvFilaIdentificador.setText(String.valueOf(persona.getId()));
    }

    @Override
    public int getItemCount() {
        return listaDePersonas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        FilaPersonaBinding binding;

        MyViewHolder(FilaPersonaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
