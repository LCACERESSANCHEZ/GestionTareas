package com.example.gestiontareas.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "_Tarea")
public class TareaVo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "_codigo")
    public int codigo;

    @ColumnInfo (name = "_titulo")
    public String titulo;

    @ColumnInfo (name = "_estado")
    public boolean estado;

    public TareaVo(String titulo, boolean estado) {
        this.titulo = titulo;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TareaVo{" +
                "codigo=" + codigo +
                ", titulo='" + titulo + '\'' +
                ", estado=" + estado +
                '}';
    }
}
