package me.chonchol.andropos.roomdb.config;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient databaseClient;
    private AndroPOSDB androPOSDB;

    public DatabaseClient(Context context) {
        this.context = context;
        androPOSDB = Room.databaseBuilder(context, AndroPOSDB.class, "ANDROPOS_DB").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if (databaseClient == null){
            databaseClient = new DatabaseClient(context);
        }
        return databaseClient;
    }

    public AndroPOSDB getAndroPOSDB() {
        return androPOSDB;
    }
}
