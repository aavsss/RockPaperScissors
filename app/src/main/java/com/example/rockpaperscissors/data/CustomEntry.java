/**
 * CustomEntry.java
 * @author Aavash Sthapit
 * Model class is created and it is combined with annotations to make it an entity
 * The class includes the elements of the object that is inserted in room database.
 * In other words, The attributes in each row of the table.
 * WARNING: Misleading variable names currently. Its use is stated in the comments.
 */
package com.example.rockpaperscissors.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Annotation to denote the table name
 * @Entity indicates Room this is going to be one of our tables.
 * With the tableName property, we indicate what name we want for the table
 */
@Entity(tableName = "contacts")
public class CustomEntry {

    /**
     * Each of the entries need a unique id to identify it.
     * We accomplish this by using the @PrimaryKey attribute.
     */
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String job;
    private String result;

    /**
     * Room uses this constructor to create entries for the table
     * @param id - unique ID of an object in the table
     * @param name - currently the user's response (attribute) of the object
     * @param job - currently random response generated (attribute) of the object
     * @param result - result (attribute) of the object
     */
    public CustomEntry(int id, String name, String job, String result){
        this.id = id;
        this.name = name;
        this.job = job;
        this.result = result;
    }

    /**
     * @Ignore makes sure that the Room database will not use this one.
     * It is for our convenience.
     * @param name  - currently the user's response (attribute) of the object
     * @param job - currently random response generated (attribute) of the object
     * @param result- result (attribute) of the object
     */
    @Ignore
    public CustomEntry(String name, String job, String result){
        this.name = name;
        this.job = job;
        this.result = result;
    }

    /**
     * Getters and setters for each variable.
     */
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
