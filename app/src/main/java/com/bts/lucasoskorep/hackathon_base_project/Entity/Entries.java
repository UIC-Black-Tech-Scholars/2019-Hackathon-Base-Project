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
    public String uid;

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo (name = "Date")
    public String date;

    @ColumnInfo (name = "Notes")
    public String notes;

    @ColumnInfo (name = "Image")
    public String imagePath;

    public Entries(){}

    public Entries( int day, int month, int year, String path){
       // this.uid = cat;
       // this.amount = amount;
        this.imagePath = path;
        this.date = month + "/" +day+ "/" + year;

    }

//    @ColumnInfo (name = "Images")
//    public

    public String getDate(){ return this.date; }

    public void setDate(int day, int month, int year){
        this.date = day + "/" + month + "/" + year;
    }

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

    public String getImage() { return imagePath; }
}
