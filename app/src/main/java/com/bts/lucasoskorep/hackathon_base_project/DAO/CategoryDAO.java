package com.bts.lucasoskorep.hackathon_base_project.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM category_table")
    List<Category> getAll();

    @Query("SELECT * FROM category_table where uid LIKE :uid")
    Category findById(int uid);

    @Query("SELECT COUNT(*) from category_table")
    int countCategories();

    @Update
    void updateCategory(Category... categories);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
