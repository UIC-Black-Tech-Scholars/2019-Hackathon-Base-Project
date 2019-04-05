package com.bts.lucasoskorep.hackathon_base_project.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table")
    List<User> getAll();

    @Query("SELECT * FROM user_table where first_name LIKE  :firstName AND last_name LIKE :lastName")
    User findByName(String firstName, String lastName);

    @Query("SELECT COUNT(*) from user_table")
    int countUsers();

    @Update
    void updateUsers(User... users);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}