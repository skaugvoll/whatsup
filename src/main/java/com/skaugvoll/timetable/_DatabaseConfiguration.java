package com.skaugvoll.timetable;

import com.mongodb.MongoClientURI;

/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class _DatabaseConfiguration {


    // API values
    public static final String databaseName = "xxxxx";
    public static final String collectionName = "xxxxx";

    public static final String usr = "xxxxxx";
    public static final String psw = "xxxxxx";
    public static final String apiKey = "xxxxxxx";


    // Driver values
    public static final MongoClientURI uri = new MongoClientURI("mongodb://"+usr+":"+psw+"@dsxxyyzz.mlab.com:29189/"+databaseName);


}
