package com.example.rockpaperscissors.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class CustomEntry {

    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String job;
    private String result;

    public CustomEntry(int id, String name, String job, String result){
        this.id = id;
        this.name = name;
        this.job = job;
        this.result = result;
    }

    @Ignore
    public CustomEntry(String name, String job, String result){
        this.name = name;
        this.job = job;
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
