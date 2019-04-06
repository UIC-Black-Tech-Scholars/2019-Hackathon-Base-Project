package com.bts.lucasoskorep.hackathon_base_project.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bts.lucasoskorep.hackathon_base_project.DAO.CategoryDAO;
import com.bts.lucasoskorep.hackathon_base_project.DAO.EntriesDao;
import com.bts.lucasoskorep.hackathon_base_project.DAO.UserDao;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

@Database(entities = {User.class, Entries.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract CategoryDAO categoryDao();

    public abstract EntriesDao entriesDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "test6-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}