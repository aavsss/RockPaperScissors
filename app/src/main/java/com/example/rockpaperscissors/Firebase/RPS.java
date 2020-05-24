/**
 * RPS.java
 * @author Aavash Sthapit
 * Entity class for each entry into the firebasefirestore
 */
package com.example.rockpaperscissors.Firebase;

import java.sql.Timestamp;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RPS {

    private String id;
    private String name;
    private String job;
    private String result;

    public RPS(){};

    public RPS(String name, String job, String result){
        setName(name);
        setJob(job);
        setResult(result);
        setId(new Timestamp(new Date().getTime()).toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    /**
     * Static method to get the mapped data from parameters for insertion into the database
     * @param userResult user's play triggered by the view they pressed in the options
     * @param randomResult random result generated
     * @param result of the game. Won, Loss, or Draw
     * @return map of the data sent it as parameters.
     */
    public static Map<String, Object> getMappedData(String userResult, String randomResult, String result){
        Map<String, Object> data = new HashMap<>();
        data.put("user_play", userResult);
        data.put("random_play", randomResult);
        data.put("result", result);
        return data;
    }

}
