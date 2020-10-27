package com.example.gestiontareas;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestiontareas.model.TareaVo;

import java.util.List;

public class Adapter_Tareas extends RecyclerView.Adapter<Adapter_Tareas.ViewHolderTareas> {

    List<TareaVo> listaTareas;
    private OnItemClickListener mListener;

    //creamos interface para poder escuchar cuando el usuario haga click
    public interface OnItemClickListener {
        void onItemClickEliminar(int position);

        void onItemClickEditar(int position);

        void onEstadoTarea(TareaVo tarea);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public Adapter_Tareas(List<TareaVo> listaTareas) {
        this.listaTareas = listaTareas;
    }

    //Inflamos item_rcv_tareas
    @NonNull
    @Override
    public ViewHolderTareas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv_tareas, parent, false);
        return new ViewHolderTareas(view, mListener);
    }

    //codigo para saber que es lo que se va a mostrar en cada componente
    @Override
    public void onBindViewHolder(@NonNull ViewHolderTareas holder, int position) {
        TareaVo lstTarea = listaTareas.get(position);
        holder.btn_Estado.setChecked(lstTarea.isEstado());
        holder.txt_titulo.setText(lstTarea.getTitulo());
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    //Creamos una instancia de item_rcv_tareas.xml con los componentes que vamos a usar
    public class ViewHolderTareas extends RecyclerView.ViewHolder {
        ToggleButton btn_Estado;
        TextView txt_titulo;
        Button btn_Editar, btn_Eliminar;

        public ViewHolderTareas(@NonNull View itemView, final Adapter_Tareas.OnItemClickListener listener) {
            super(itemView);
            //casteamos componentes de item_rcv_tareas.xml
            txt_titulo = (TextView) itemView.findViewById(R.id.txt_Titulo);
            btn_Estado = (ToggleButton) itemView.findViewById(R.id.btn_Estado);
            btn_Editar = (Button) itemView.findViewById(R.id.btn_Editar);
            btn_Eliminar = (Button) itemView.findViewById(R.id.btn_Eliminar);

            //Escuchamos cuando el usuario seleccione en los botones (Eliminar, Editar, Estado)
            btn_Eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int posicion = getAdapterPosition();
                        if (posicion != RecyclerView.NO_POSITION) {
                            mListener.onItemClickEliminar(posicion);
                        }
                    }
                }
            });
            btn_Editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int posicion = getAdapterPosition();
                        if (posicion != RecyclerView.NO_POSITION) {
                            mListener.onItemClickEditar(posicion);
                        }
                    }
                }
            });
            btn_Estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int posicion = getAdapterPosition();
                    TareaVo _tarea = listaTareas.get(posicion);
                    _tarea.setEstado(b);
                    mListener.onEstadoTarea(_tarea);
                }
            });
        }
    }
}
