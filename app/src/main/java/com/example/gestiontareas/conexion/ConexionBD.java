package com.example.gestiontareas.conexion;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestiontareas.dao.ITareaDao;
import com.example.gestiontareas.model.TareaVo;


@Database(entities = {TareaVo.class}, version = 1)
public abstract class ConexionBD extends RoomDatabase {

    private static ConexionBD INSTANCE;

    public abstract ITareaDao iTareaDao();

    public static ConexionBD getAppDataBase(Context context){
        if (null == INSTANCE) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ConexionBD.class,"COMSATEL_BD").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
