package com.bts.lucasoskorep.hackathon_base_project.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;

import java.util.List;

@Dao
public interface EntriesDao {

    @Query("SELECT * FROM entries_table")
    List<Entries> getAll();

    @Query("SELECT * FROM entries_table where nameTitle LIKE  :nameTitle")
    Entries findByNameTitle(String nameTitle);

    @Query("SELECT COUNT(*) from entries_table")
    int countEntries();

    @Update
    void updateEntries(Entries... entries);

    @Insert
    void insertAll(Entries... entries);

    @Delete
    void delete(Entries entries);
}