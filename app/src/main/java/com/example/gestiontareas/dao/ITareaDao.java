package com.example.gestiontareas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestiontareas.model.TareaVo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ITareaDao {
    @Insert
    public void addTarea(TareaVo tarea);

    @Query("SELECT * FROM _Tarea")
    public List<TareaVo> getTarea();

    @Delete
    public void deleteTarea (TareaVo tarea);

    @Update
    public void updateTarea (TareaVo tarea);
}
