package com.bts.lucasoskorep.hackathon_base_project.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "entries_table")
public class Entries {

    @PrimaryKey(autoGenerate = true)
    private int transPrimaryKey;

    @ColumnInfo(name = "nameTitle")
    private String nameTitle;

    @ColumnInfo(name = "Category")
    public int uid;

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo (name = "Date")
    public int date;

    @ColumnInfo (name = "Notes")
    public String notes;

//    @ColumnInfo (name = "Images")
//    public

    @ColumnInfo(name = "Category")
    public int catPrimaryKey;

    public int getTransPrimaryKey() {
        return transPrimaryKey;
    }

    public void setTransPrimaryKey(int transPrimaryKey) {
        this.transPrimaryKey = transPrimaryKey;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public int getCatPrimaryKey() {
        return catPrimaryKey;
    }

    public void setCatPrimaryKey (int catPrimaryKey) {
        this.catPrimaryKey = catPrimaryKey;
    }
}
