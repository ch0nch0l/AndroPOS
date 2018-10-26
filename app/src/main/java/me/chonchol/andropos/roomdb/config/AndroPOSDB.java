package me.chonchol.andropos.roomdb.config;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.chonchol.andropos.model.User;
import me.chonchol.andropos.roomdb.dao.IClientUrlDao;

//@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AndroPOSDB extends RoomDatabase{
    public abstract IClientUrlDao clientUrlDao();
}