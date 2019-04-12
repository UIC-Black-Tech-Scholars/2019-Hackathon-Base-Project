package com.bts.lucasoskorep.hackathon_base_project.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "entries_table")
public class Entries {

    @PrimaryKey(autoGenerate = true)
    private int transPrimaryKey;

    @ColumnInfo (name = "ID")
    private int ID;

    @ColumnInfo(name = "nameTitle")
    private String nameTitle;

    @ColumnInfo(name = "Category")
    public String uid;

    @ColumnInfo(name = "Amount")
    public int amount;

    @ColumnInfo (name = "Date")
    public String date;

    @ColumnInfo (name = "Month")
    public int month;

    @ColumnInfo (name = "Year")
    public int year;

    @ColumnInfo (name = "Day")
    public int day;

    @ColumnInfo (name = "Notes")
    public String notes;

    @ColumnInfo (name = "Image")
    public String imagePath;

    public Entries(){}

    public Entries( int day, int month, int year, String path, int id, int amount, String cat){
       // this.uid = cat;
       // this.amount = amount;
        this.imagePath = path;
        this.year = year;
        this.day = day;
        this.month = month;
        this.uid = cat;
        this.date = month + "/" +day+ "/" + year;
        this.ID = id;
        this.amount = amount;
        this.uid = cat;

    }

//    @ColumnInfo (name = "Images")
//    public

    public int getID(){ return this.ID; }
    public int getAmount(){ return this.amount; }
    public String getDate(){ return this.date; }

    public void setDate(int day, int month, int year){
        this.date = day + "/" + month + "/" + year;
    }

    public void setID(int id){this.ID = id; }

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

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }



    public String getUid() {
        return uid;
    }
}
