package com.example.gestiontareas;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.gestiontareas.conexion.ConexionBD;
import com.example.gestiontareas.dao.ITareaDao;
import com.example.gestiontareas.model.TareaVo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    ITareaDao daoTarea;
    List<TareaVo> listaTareas;
    RecyclerView recyclerView_Tareas;
    Adapter_Tareas mAdapter;
    TareaVo tareaVo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daoTarea = ConexionBD.getAppDataBase(this.getApplicationContext()).iTareaDao();

        //codigo para poder mostrar recyclerView
        recyclerView_Tareas = (RecyclerView) findViewById(R.id.idRecyclerTareas);
        recyclerView_Tareas.setHasFixedSize(true);
        recyclerView_Tareas.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAbrirDialogo();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListar();
    }

    //Este metodo se encarga de abrir un dialog para poder empezar agregar registros a la entidad TareaVo
    public void mAbrirDialogo() {
        final View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        view = inflater.inflate(R.layout.show_dialog_agregar, null);
        builder.setView(view);

        //casteamos componente del dialogo creado para agregar nueva tarea show_dialog_agregar.xml
        Button btn_Agregar = (Button) view.findViewById(R.id.btn_Agregar);
        Button btn_Cancelar = (Button) view.findViewById(R.id.btn_Cancelar);
        TextInputEditText txt_NuevaTarea = (TextInputEditText) view.findViewById(R.id.txt_NuevaTarea);

        //abrimos dialogo show_dialog_agregar.xml
        final AlertDialog dialog = builder.create();
        dialog.show();

        //En el caso que el usuario seleccione agregar validara que el campo txt_NuevaTarea no sea nulo y que tenga menos de 18 caracteres
        //solo asi se podra agregar una nueva tarea, luego se vera reflejado en el RecyclerView
        btn_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevaTarea = txt_NuevaTarea.getText().toString();
                if (nuevaTarea.length() <= 18 && nuevaTarea.isEmpty() != true) {
                    daoTarea.addTarea(new TareaVo(nuevaTarea, false));
                    dialog.dismiss();
                    mListar();
                    Toast.makeText(getApplication(), "TAREA AGREGADA: " + nuevaTarea, Toast.LENGTH_SHORT).show();
                } else {
                }
            }
        });
        //En el caso que el usuario seleccione cancelar el dialogo se cerrara
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //Metodo para listar las tareas guardas
    public void mListar() {
        //Obtengo la lista de tareas
        listaTareas = daoTarea.getTarea();
        //El adapter me pide la lista a mostrar
        mAdapter = new Adapter_Tareas(listaTareas);

        //Acciones del adapter: Eliminar, Editar, Estado de la tarea(true o false)
        mAdapter.setOnItemClickListener(new Adapter_Tareas.OnItemClickListener() {
            @Override
            public void onItemClickEliminar(int position) {
                mAbrirDialogoEliminar(position);
            }

            @Override
            public void onItemClickEditar(int position) {
                mAbrirDialogoEditar(position);
            }

            //Edita el estado de la tarea si se selecciona (true o false)
            @Override
            public void onEstadoTarea(TareaVo tarea) {
                daoTarea.updateTarea(tarea);
            }
        });

        //le enviamos el adapter al recyclerView
        recyclerView_Tareas.setAdapter(mAdapter);
        //actualizamos la lista
        mAdapter.notifyDataSetChanged();
    }

    //Metodo para eliminar una tarea
    public void mAbrirDialogoEliminar(final int position) {
        //capturamos la posicion de la lista y lo guardamos en el objeto tipo TareaVo
        tareaVo = listaTareas.get(position);
        final View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        view = inflater.inflate(R.layout.show_dialog_eliminar, null);
        builder.setView(view);

        //casteamos componente del dialogo creado para agregar nueva tarea show_dialog_eliminar.xml
        Button btn_Aceptar = (Button) view.findViewById(R.id.btn_Aceptar);
        Button btn_Cancelar = (Button) view.findViewById(R.id.btn_Cancelar);
        TextView txt_Titulo = (TextView) view.findViewById(R.id.txt_TituloEliminar);

        //abrimos dialogo show_dialog_agregar.xml
        final AlertDialog dialog = builder.create();
        dialog.show();

        //mostramos el titulo del objeto que vamos a eliminar
        txt_Titulo.setText(tareaVo.getTitulo().toUpperCase().toString());

        //En el caso que el usuario seleccione aceptar se eliminara el objeto, se eliminara el registro de la lista segun la posicion
        // y la lista sera actualizada
        btn_Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(), "ELIMINADO", Toast.LENGTH_SHORT).show();
                daoTarea.deleteTarea(tareaVo);
                listaTareas.remove(position);
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        //En el caso que el usuario seleccione cancelar el dialogo se cerrara
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    //Metodo para editar una tarea
    public void mAbrirDialogoEditar(final int position) {
        //capturamos la posicion de la lista y lo guardamos en el objeto tipo TareaVo
        tareaVo = listaTareas.get(position);
        final View view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        view = inflater.inflate(R.layout.show_dialog_agregar, null);
        builder.setView(view);

        //casteamos componente del dialogo creado para agregar nueva tarea show_dialog_agregar.xml
        Button btn_Agregar = (Button) view.findViewById(R.id.btn_Agregar);
        Button btn_Cancelar = (Button) view.findViewById(R.id.btn_Cancelar);
        TextInputLayout txt_EditarTarea = (TextInputLayout) view.findViewById(R.id.txtl_NuevaTarea);
        TextInputEditText txt_NuevoTitulo = (TextInputEditText) view.findViewById(R.id.txt_NuevaTarea);

        //abrimos dialogo show_dialog_agregar.xml
        final AlertDialog dialog = builder.create();
        dialog.show();

        //como usamos el mismo dialog(show_dialog_agregar.xml) cambiamos el texto de algunos componentes unicamente por un tema visual hacia el usuario
        txt_EditarTarea.setHint("Editar tarea");
        txt_NuevoTitulo.setText(tareaVo.getTitulo());
        btn_Agregar.setText("ACEPTAR");

        //En el caso que el usuario seleccione aceptar se editara unicamente el titulo, dejando el valor estado tal y como esta
        // la lista se actualizara por la posicion indicada y el campo titulo actualizado.
        //El dialogo se cierra y se actualiza la lista
        btn_Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editarTitulo = txt_NuevoTitulo.getText().toString();
                if (editarTitulo.length() <= 18 && editarTitulo.isEmpty() != true) {
                    tareaVo.setTitulo(editarTitulo);
                    tareaVo.setEstado(tareaVo.isEstado());
                    listaTareas.set(position, tareaVo);
                    daoTarea.updateTarea(tareaVo);
                    dialog.dismiss();
                    mListar();
                    Toast.makeText(getApplication(), "TAREA EDITADA: " + editarTitulo, Toast.LENGTH_SHORT).show();
                } else {
                }
            }
        });
        //En el caso que el usuario seleccione cancelar el dialogo se cerrara
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}